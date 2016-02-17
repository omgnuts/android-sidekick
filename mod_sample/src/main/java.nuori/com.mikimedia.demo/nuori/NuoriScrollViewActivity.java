package com.mikimedia.demo.nuori;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mikimedia.android.component.PicassoTopCropTransform;
import com.mikimedia.android.nuori.Nuori;
import com.mikimedia.android.nuori.NuoriParallaxScrollView;
import com.mikimedia.demo.ImageLoader;
import com.mikimedia.demo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public class NuoriScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuori_scrollview_activity);

        final View headerView = findViewById(R.id.header_view);
        final ImageView imageView = (ImageView) findViewById(R.id.image);

        NuoriParallaxScrollView scrollView = (NuoriParallaxScrollView) findViewById(R.id.scroll_view);
        final Nuori nuori = Nuori.from(scrollView)
                .setImageView(imageView)
                .setHeaderView(headerView)
                .into(false);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                nuori.setImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                nuori.setImage(errorDrawable);
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                nuori.setImage(placeHolderDrawable);
            }
        };

        final Point size = nuori.getPreCachedSize();
        final Transformation transformer = new PicassoTopCropTransform(size.x, size.y);
        ImageLoader.with(this).loadSampleImage(target, transformer);

//        imageView.setImageResource(R.mipmap.guitar);
//        imageView.setImageResource(R.mipmap.ic_launcher);
//        imageView.setImageResource(R.mipmap.horizontal_image);
//        imageView.setImageResource(R.mipmap.vertical_long);
//        ImageLoader.with(this).loadSampleImage(target);
//        ImageLoader.with(this).loadSampleImage(target, 4);
//        ImageLoader.with(this).loadSampleImage(imageView);
//        ImageLoader.with(this).loadSampleImage(imageView, 4);

    }

}