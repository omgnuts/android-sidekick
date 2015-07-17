package com.mikimedia.demo.imageslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikimedia.android.component.ImageSliderController;
import com.mikimedia.demo.ImageLoader;
import com.squareup.picasso.Target;

public class ImageSliderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageSliderController isc = new ImageSliderController(this);
        setContentView(isc.onCreate());
        isc.init(new DataAdapter(ImageLoader.dataUri), ImageLoader.with(this).getOnErrorDrawable());
    }

    private class DataAdapter implements ImageSliderController.ImageDataAdapter {

        private final String[] imgUri;

        private DataAdapter(String[] imgUri) {
            this.imgUri = imgUri;
        }

        @Override
        public int size() {
            return imgUri.length;
        }

        @Override
        public void bindView(int position, Target target) {
            String asset = imgUri[position];
            ImageLoader.with(ImageSliderActivity.this).load(asset, target);
        }

    }


}