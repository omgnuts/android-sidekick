package com.mikimedia.demo.imageslider;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mikimedia.android.component.ImageSliderController;
import com.mikimedia.demo.ImageLoader;
import com.squareup.picasso.Target;

public class ImageSliderActivity extends AppCompatActivity {

    private static String[] dataUri;

    static {
        dataUri = new String[]{
                "http://images6.backpage.com/imager/u/large/115446428/3-1.jpg",
                "http://images6.backpage.com/imager/u/large/115446386/2-2.jpg",
                "http://images5.backpage.com/imager/u/large/114891859/Shirley2.jpg"
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageSliderController isc = new ImageSliderController(this);
        setContentView(isc.onCreate());
        isc.init(new DataAdapter(dataUri));
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
            ImageLoader.get(ImageSliderActivity.this).load(asset, target);
        }

    }


}