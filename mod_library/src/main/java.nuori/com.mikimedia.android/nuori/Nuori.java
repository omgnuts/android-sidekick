package com.mikimedia.android.nuori;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.mikimedia.android.R;

public class Nuori {

    public static Nuori from(NuoriParallaxListView view) {
        return view.getNuori();
    }

    /**
     * Activated is only set true after prepare is called.
     * This is to prevent scrolling when not ready.
     */
    private boolean activated = false;

    private final NuoriParallaxListView parent;
    private ImageView imageView = null;
    private View headerView = null;

    /**
     * Sets the image view height relative to the screen
     */
    private float mHeightToScreen = 0.5f;
    private int mHeightInPixels = -1;

    /**
     * Determines the theoretical zoom for the image view
     */
    private float mZoomRatio = 2.0f;
    private int mMaxZoomHeight = -1;

    /**
     * more like the mHeightInPixels
     */
    private int mImageViewHeight = -1;

    Nuori(NuoriParallaxListView parent) {
        this.parent = parent;
    }

    void initFromAttributes(Context context, AttributeSet attrs,
                                 int defStyleAttr, int defStyleRes) {
        // Read and apply provided attributes
        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.NuoriParallaxListView, defStyleAttr, defStyleRes);
        mZoomRatio = a.getFloat(R.styleable.NuoriParallaxListView_zoomRatio, mZoomRatio);
        mHeightToScreen = a.getFloat(R.styleable.NuoriParallaxListView_heightToScreenRatio, mHeightToScreen);
        a.recycle();

//        /**
//         * TODO: additional attributes to be set
//         */
//        mHeightInPixels = context.getResources()
//                .getDimensionPixelSize(R.dimen.nuori_size_default_height);

    }

    public Nuori setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    View getHeaderView() {
        return headerView;
    }

    public Nuori setHeaderView(View headerView) {
        this.headerView = headerView;
        return this;
    }

    public Nuori setZoomRatio(float mZoomRatio) {
        this.mZoomRatio = mZoomRatio;
        return this;
    }

    public void into() {

        if (imageView == null) {
            throw new NullPointerException("No ImageView has been set");
        }

        if (headerView == null) {
            throw new NullPointerException("No header view has been set");
        }

        if (mZoomRatio < 1.0) {
            throw new IllegalStateException("ZoomRatio must be larger than 1.0");
        }

        prepare();

        parent.setNuori(this);
    }

    private void prepare() {

        parent.post(new Runnable() {
            @Override
            public void run() {
                setViewsBounds(mZoomRatio);
            }
        });

        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        bounceBack = new BounceBackAnimation(imageView);

        mHeightInPixels = (int) (mHeightToScreen * parent.getContext()
                .getResources().getDisplayMetrics().heightPixels);

        System.out.println("......... image size = " + mHeightInPixels);

        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager wm = (WindowManager) parent.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        System.out.println("......... screensize  = " + metrics.heightPixels);

        activated = true;
    }

    public void setViewsBounds(double zoomRatio) {
        if (mImageViewHeight == -1) {
            mImageViewHeight = imageView.getHeight();
            System.out.println("......... mImageViewHeight  = " + mImageViewHeight);

            if (mImageViewHeight <= 0) {
                mImageViewHeight = mHeightInPixels;
            }

            double ratio =  ((double) imageView.getWidth()) / ((double) imageView.getDrawable().getIntrinsicWidth());
            mMaxZoomHeight = (int) (imageView.getDrawable().getIntrinsicHeight() * ratio * zoomRatio);
        }
    }

    void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (!activated) return;

        View firstView = (View) imageView.getParent();
        // firstView.getTop < getPaddingTop means mImageView will be covered by top padding,
        // so we can layout it to make it shorter
        if (firstView.getTop() < parent.getPaddingTop() && imageView.getHeight() > mImageViewHeight) {
            imageView.getLayoutParams().height = Math.max(imageView.getHeight() - (parent.getPaddingTop() - firstView.getTop()), mImageViewHeight);
            // to set the firstView.mTop to 0,
            // maybe use View.setTop() is more easy, but it just support from Android 3.0 (API 11)
            firstView.layout(firstView.getLeft(), 0, firstView.getRight(), firstView.getHeight());
            imageView.requestLayout();
        }
    }


    boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                         int scrollY, int scrollRangeX, int scrollRangeY,
                         int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (!activated) return false;

        deltaY *= 5;

        if (imageView.getHeight() <= mMaxZoomHeight && isTouchEvent) {
            if (deltaY < 0) {
                if (imageView.getHeight() - deltaY / 2 >= mImageViewHeight) {
                    imageView.getLayoutParams().height = imageView.getHeight() - deltaY / 2 < mMaxZoomHeight ?
                            imageView.getHeight() - deltaY / 2 : mMaxZoomHeight;
                    imageView.requestLayout();
                }
            } else {
                if (imageView.getHeight() > mImageViewHeight) {
                    imageView.getLayoutParams().height = imageView.getHeight() - deltaY > mImageViewHeight ?
                            imageView.getHeight() - deltaY : mImageViewHeight;
                    imageView.requestLayout();
                    return true;
                }
            }
        }
        return false;
    }

    void onTouchEvent(MotionEvent ev) {
        if (!activated) return;

        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                bounceBack.cancel();
                break;

            case MotionEvent.ACTION_UP:
                if (mImageViewHeight < imageView.getHeight()) {
                    bounceBack.start(mImageViewHeight);
                }
                break;
        }
    }

    private BounceBackAnimation bounceBack = null;

    /**
     * Cant really use matrix transformation here, because the listview items layout
     * will depend on the actual physical layout of the imageview
     */
    private static class BounceBackAnimation extends Animation implements Animation.AnimationListener {
        private int targetHeight;
        private int originalHeight;
        private int extraHeight;
        private final View view;

        private BounceBackAnimation(View view) {
            this.view = view;
            setDuration(200);
            setAnimationListener(this);
//            setFillAfter(true);
        }

        private void start(int targetHeight) {
            this.targetHeight = targetHeight;
            this.originalHeight = view.getHeight();
            this.extraHeight = targetHeight - originalHeight;
            view.startAnimation(this);
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
//            System.out.println("applyTransformation " + interpolatedTime);
            int newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            view.getLayoutParams().height = newHeight;
            view.requestLayout();
        }

        public void cancel() {
            if (hasStarted()) {
                super.cancel();

                /** Its not super necessary to clear animation here
                 * because it is cleared on animation end.
                 * But just putting a hard stop here in case
                 * a threading lag due to GC or stuff causes
                 * another applyTransformation to occur
                 */
                view.clearAnimation();

                /**
                 * Check if the API level supports canceling existing animations via the
                 * ViewPropertyAnimator, and cancel as a brute force measure :)
                 */

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    view.animate().cancel();
                }
            }
        }

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            /**
             * Do not remove this. There is an android bug that keeps the
             * animation running (i.e applyTransformation is being called)
             * even when it is supposedly ended. This is a very bad android bug.
             */
            view.clearAnimation();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

}
