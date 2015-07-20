package com.mikimedia.demo.nuori;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

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


        final ImageView imageView = (ImageView) findViewById(R.id.image);

        NuoriParallaxScrollView scrollView = (NuoriParallaxScrollView) findViewById(R.id.scroll_view);
        final Nuori nuori = Nuori.from(scrollView)
                .setImageView(imageView)
                .into(false);

        final Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                imageView.setImageBitmap(bitmap);
                nuori.notifyViewBoundsChanged();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                imageView.setImageDrawable(errorDrawable);
                nuori.notifyViewBoundsChanged();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                imageView.setImageDrawable(placeHolderDrawable);
                nuori.notifyViewBoundsChanged();
            }
        };

        final Point size = nuori.getCropSize();
//        ImageLoader.with(this).loadSampleImage(target, size);
        final BitmapTransform transformer = new BitmapTransform(size.x, size.y);
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

    private static class BitmapTransform implements  Transformation {

        private final int width;
        private final int height;

        private final Matrix matrix;

        private BitmapTransform(int width, int height) {
            this.width = width;
            this.height = height;

            this.matrix = new Matrix();
        }

        @Override
        public Bitmap transform(Bitmap source) {

            int bw = source.getWidth();
            int bh = source.getHeight();

            float scale = (width * bh > height * bw) ? (float) width / bw : (float) height / bh;

            float nw, nh;
            float dx;

            if (scale > 1.0) {

                nw = width * 1.0f / scale;
                nh = height * 1.0f / scale;
                dx = (bw - nw) * 0.5f;
                scale = 1.0f;
            } else {
                nw = width;
                nh = height;
                dx = (bw - nw) * 0.5f;
            }

            Log.d("NN", "bw = " + bw);
            Log.d("NN", "bh = " + bh);

            Log.d("NN", "w/bw = " + (float) width / bw);
            Log.d("NN", "h/bh = " + (float) height / bh);

            Log.d("NN", "nw = " + nw);
            Log.d("NN", "nh = " + nh);

            Log.d("NN", "scale = " + scale);
            Log.d("NN", "dx = " + dx);

            matrix.preTranslate(dx, 0);
//            matrix.setScale(scale, scale);

            Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, (int)nw, (int)nh, matrix, true);
            if (!bitmap.equals(source)) {
                source.recycle();
            }

            return bitmap;

        }

        @Override
        public String key() {
            return "nuori";
        }
    }

}