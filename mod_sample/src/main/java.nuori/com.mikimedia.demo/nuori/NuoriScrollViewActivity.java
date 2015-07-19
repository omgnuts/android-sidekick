package com.mikimedia.demo.nuori;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.mikimedia.android.nuori.Nuori;
import com.mikimedia.android.nuori.NuoriParallaxScrollView;
import com.mikimedia.demo.R;

public class NuoriScrollViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.nuori_scrollview_activity);


        ImageView imageView = (ImageView) findViewById(R.id.image);

        NuoriParallaxScrollView scrollView = (NuoriParallaxScrollView) findViewById(R.id.scroll_view);
        Nuori.from(scrollView)
                .setImageView(imageView)
                .into();

//        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setImageResource(R.mipmap.horizontal_image);
//        ImageLoader.with(this).loadSampleImage(imageView);


    }

}