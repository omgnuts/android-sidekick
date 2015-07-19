package com.mikimedia.android.nuori;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView;
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


    public final static double NO_ZOOM = 1;
    public final static double ZOOM_X2 = 2;

//    private ImageView mImageView;
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
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (mImageViewHeight - 1 < imageView.getHeight()) {
                ResetAnimimation animation = new ResetAnimimation(
                        imageView, mImageViewHeight);
                animation.setDuration(300);
                imageView.startAnimation(animation);
            }
        }
    }

    boolean overScrollBy(int deltaX, int deltaY, int scrollX,
                         int scrollY, int scrollRangeX, int scrollRangeY,
                         int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

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

    private static class ResetAnimimation extends Animation {
        int targetHeight;
        int originalHeight;
        int extraHeight;
        View mView;

        protected ResetAnimimation(View view, int targetHeight) {
            this.mView = view;
            this.targetHeight = targetHeight;
            originalHeight = view.getHeight();
            extraHeight = this.targetHeight - originalHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {

            int newHeight;
            newHeight = (int) (targetHeight - extraHeight * (1 - interpolatedTime));
            mView.getLayoutParams().height = newHeight;
            mView.requestLayout();
        }
    }
}
