package com.mikimedia.demo.nuori;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.mikimedia.android.component.PicassoTopCropTransform;
import com.mikimedia.android.nuori.Nuori;
import com.mikimedia.android.nuori.NuoriParallaxListView;
import com.mikimedia.demo.ImageLoader;
import com.mikimedia.demo.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

public class NuoriListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuori_listview_activity);

        NuoriParallaxListView listView = (NuoriParallaxListView) findViewById(R.id.list_view);
        View header = LayoutInflater.from(this).inflate(R.layout.nuori_listview_header, listView, false);
        final ImageView imageView = (ImageView) header.findViewById(R.id.image);
        final Nuori nuori = Nuori.from(listView)
                .setImageView(imageView)
                .setHeaderView(header)
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

//        imageView.setImageResource(R.mipmap.ic_launcher);
//        imageView.setImageResource(R.mipmap.horizontal_image);
//        imageView.setImageResource(R.mipmap.vertical_long);
//        ImageLoader.with(this).loadSampleImage(target);
//        ImageLoader.with(this).loadSampleImage(target, 4);
//        ImageLoader.with(this).loadSampleImage(imageView);
//        ImageLoader.with(this).loadSampleImage(imageView, 4);


        String[] items = new String[200];
        for (int c = 0; c < items.length; c++) {
            items[c] = "String " + c;
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
            this, android.R.layout.simple_list_item_1, items
        );

        listView.setAdapter(adapter);

    }


}