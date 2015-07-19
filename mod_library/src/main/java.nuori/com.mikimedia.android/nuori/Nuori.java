package com.mikimedia.android.nuori;

import android.view.View;
import android.widget.ImageView;

public class Nuori {

    private final NuoriParallaxListView parent;

    private final ImageView imageView;

    private final View headerView;

    private Nuori(NuoriParallaxListView parent,
          ImageView imageView,
          View headerView) {
        this.parent = parent;
        this.imageView = imageView;
        this.headerView = headerView;

        prepare();
    }

    private void prepare() {
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    public ImageView getImageView() {
        return imageView;
    }

    public View getHeaderView() {
        return headerView;
    }

    public static class Builder {

        public static Builder init() {
            return new Builder();
        }

        public void into(NuoriParallaxListView parent) {

            if (parent == null) {
                throw new NullPointerException("Unable to attach to a null parent");
            }

            if (imageView == null) {
                throw new NullPointerException("No ImageView has been set");
            }

            if (headerView == null) {
                throw new NullPointerException("No header view has been set");
            }

            Nuori nuori = new Nuori(parent, imageView, headerView);

            parent.setNuori(nuori);
        }

        private ImageView imageView = null;

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        private View headerView = null;

        public Builder setHeaderView(View headerView) {
            this.headerView = headerView;
            return this;
        }

    }

}
