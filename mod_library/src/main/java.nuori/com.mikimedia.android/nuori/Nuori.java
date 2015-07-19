package com.mikimedia.android.nuori;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;

import com.mikimedia.android.R;

public class Nuori implements AbsListView.OnScrollListener {

    public static Nuori from(NuoriParallaxListView view) {
        return view.getNuori();
    }

    private final NuoriParallaxListView parent;

    private ImageView imageView = null;

    private View headerView = null;

    private boolean activated = false;

    private int mDefaultImageViewHeight;

    Nuori(NuoriParallaxListView parent) {
        this.parent = parent;
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

        activated = true;
    }

    void init(Context context, AttributeSet attrs) {
        mDefaultImageViewHeight = context.getResources()
                .getDimensionPixelSize(R.dimen.nuori_size_default_height);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }
}
