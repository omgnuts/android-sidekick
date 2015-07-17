/*
 *
 *  * Copyright (c) 2015. The MikiMedia Inc
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.mikimedia.android.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class SubSamplingTargetView extends SubsamplingScaleImageView implements Target {

    private static final String TAG = SubSamplingTargetView.class.getSimpleName();

    public abstract static class SimpleOnImageEventListener implements OnImageEventListener {

        @Override
        public void onReady() {

        }

        @Override
        public void onImageLoaded() {

        }

        @Override
        public void onPreviewLoadError(Exception e) {

        }

        @Override
        public void onImageLoadError(Exception e) {

        }

        @Override
        public void onTileLoadError(Exception e) {

        }
    }

    public SubSamplingTargetView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    public SubSamplingTargetView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setCacheEnabled(true);
        setMinimumDpi(50);
        setDoubleTapZoomDpi(120); // default base 160dpi
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        setImage(ImageSource.bitmap(bitmap));
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        setImage(ImageSource.bitmap(drawableToBitmap(errorDrawable)));
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
        setImage(ImageSource.bitmap(drawableToBitmap(placeHolderDrawable)));
    }

    private static final Bitmap drawableToBitmap (Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        Bitmap bitmap = null;

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }


}
