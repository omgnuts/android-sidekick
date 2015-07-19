package com.mikimedia.android.nuori;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.mikimedia.android.R;

public class Nuori {

    public static Nuori from(NuoriParallaxListView view) {
        return view.getNuori();
    }

    private final NuoriParallaxListView parent;

    private ImageView imageView = null;

    private View headerView = null;

    private boolean activated = false;

    Nuori(NuoriParallaxListView parent) {
        this.parent = parent;
    }

    void init(Context context, AttributeSet attrs) {
        mDefaultImageViewHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.nuori_size_default_height);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Nuori setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public View getHeaderView() {
        return headerView;
    }

    public Nuori setHeaderView(View headerView) {
        this.headerView = headerView;
        return this;
    }

    public void into() {

        if (parent == null) {
            throw new NullPointerException("Unable to attach to a null parent");
        }

        if (imageView == null) {
            throw new NullPointerException("No ImageView has been set");
        }

        if (headerView == null) {
            throw new NullPointerException("No header view has been set");
        }

        prepare();

        parent.setNuori(this);
    }

    private void prepare() {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        parent.post(new Runnable() {
            @Override
            public void run() {
                setViewsBounds(ZOOM_X2);
            }
        });

        activated = true;
    }


    public final static double ZOOM_X2 = 4;

    private int mDrawableMaxHeight = -1;
    private int mImageViewHeight = -1;
    private int mDefaultImageViewHeight = 0;

    public void setViewsBounds(double zoomRatio) {
        if (mImageViewHeight == -1) {
            mImageViewHeight = imageView.getHeight();
            if (mImageViewHeight <= 0) {
                mImageViewHeight = mDefaultImageViewHeight;
            }
            double ratio = ((double) imageView.getDrawable().getIntrinsicWidth()) / ((double) imageView.getWidth());

            mDrawableMaxHeight = (int) ((imageView.getDrawable().getIntrinsicHeight() / ratio) * (zoomRatio > 1 ?
                    zoomRatio : 1));
        }
    }

    void onScrollChanged(int l, int t, int oldl, int oldt) {

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

    void onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            resetter.cancel();
        }
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mImageViewHeight - 1 < imageView.getHeight()) {
                resetter.reset(imageView, mImageViewHeight);
                imageView.startAnimation(resetter);
            }
        }
    }

    boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                         int scrollY, int scrollRangeX, int scrollRangeY,
                         int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
//        deltaY *= 5;

        if (imageView.getHeight() <= mDrawableMaxHeight && isTouchEvent) {
            if (deltaY < 0) {
                if (imageView.getHeight() - deltaY / 2 >= mImageViewHeight) {
                    imageView.getLayoutParams().height = imageView.getHeight() - deltaY / 2 < mDrawableMaxHeight ?
                            imageView.getHeight() - deltaY / 2 : mDrawableMaxHeight;
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

    private final ResetAnimimation2 resetter = new ResetAnimimation2();

    private static class ResetAnimimation2 extends Animation implements Animation.AnimationListener {
        int targetHeight;
        int originalHeight;
        int extraHeight;
        View mView;

        private ResetAnimimation2() {
            setDuration(10000);
            setFillAfter(true);
        }

        /**
         * Returns true if the API level supports canceling existing animations via the
         * ViewPropertyAnimator, and false if it does not
         * @return true if the API level supports canceling existing animations via the
         * ViewPropertyAnimator, and false if it does not
         */
        public static boolean canCancelAnimation() {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
        }

        int count = 0;
        boolean hasCancelled = false;
        public void cancel() {
            if (hasStarted()) {
                super.cancel();
                hasCancelled = true;
                if (canCancelAnimation()) {
                    mView.animate().cancel();
                }
                mView.clearAnimation();
            }
        }


        private void reset(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;

            System.out.println("originalHeight " + originalHeight);
            System.out.println("targetHeight " + targetHeight);
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            System.out.println("applyTransformation " + interpolatedTime);
            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();

            currHeight = newHeight;
        }

        int currHeight = 0;

        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
//            if (hasCancelled) {
//                System.out.println("onAnimationCancel ");
//                mView.clearAnimation();
//                mView.getLayoutParams().height = currHeight;
//                mView.requestLayout();
//                hasCancelled = false;
//            }
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }

//    private static class ResetAnimimation extends Animation {
//        int targetHeight;
//        int originalHeight;
//        int extraHeight;
//        View mView;
//
//        protected ResetAnimimation(View view, int targetHeight) {
//            this.mView = view;
//            this.targetHeight = targetHeight;
//            originalHeight = view.getHeight();
//            extraHeight = this.targetHeight - originalHeight;
//        }
//
//
//        @Override
//        protected void applyTransformation(float interpolatedTime,
//                                           Transformation t) {
//
//            int newHeight;
//            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
//            mView.getLayoutParams().height = newHeight;
//            mView.requestLayout();
//        }
//    }
}
