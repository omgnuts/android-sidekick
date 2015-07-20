package com.mikimedia.android.component;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class PicassoTopCropTransform implements Transformation {

    private final int width;
    private final int height;
    private final Matrix matrix;

    public PicassoTopCropTransform(int width, int height) {
        this.width = width;
        this.height = height;
        this.matrix = new Matrix();
    }

    @Override
    public Bitmap transform(Bitmap source) {

        int bw = source.getWidth();
        int bh = source.getHeight();

        float scale = (width * bh > height * bw) ? (float) width / bw : (float) height / bh;

        float nw = width;
        float nh = height;

        if (scale > 1.0) {
            float scaleInverse = 1.0f / scale;
            nw *= scaleInverse;
            nh *= scaleInverse;
            scale = 1.0f;
        }

        int dx = (int) ((bw - nw) * 0.5f);
        matrix.setScale(scale, scale);

//            Log.d("NN", "width = " + width);
//            Log.d("NN", "height = " + height);
//            Log.d("NN", "bw = " + bw);
//            Log.d("NN", "bh = " + bh);
//            Log.d("NN", "scale = " + scale);
//            Log.d("NN", "width / bw = " + (float) width / bw);
//            Log.d("NN", "height / bh = " + (float)  height / bh);
//            Log.d("NN", "nw = " + nw);
//            Log.d("NN", "nh = " + nh);
//            Log.d("NN", "dx = " + dx);

        Bitmap bitmap = Bitmap.createBitmap(source, dx, 0, (int)nw, (int)nh, matrix, false);
        if (!bitmap.equals(source)) {
            source.recycle();
        }

        return bitmap;
    }

    @Override
    public String key() {
        return "top_crop";
    }
}