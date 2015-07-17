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

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import com.mikimedia.android.util.ColorMaker;

public class IndicatorView extends View {

    public IndicatorView(Context context) {
        super(context);
        init();
    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public IndicatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private float radius;
    private float alphaRadius;
    private Paint brush;
    private Paint alphaBrush;

    private int size;
    private int position;
    private int gap = 8;

    private int alpha;

    final float dm = getContext().getResources().getDisplayMetrics().density;

    private void init() {
        radius = 4.0f * dm;
        alphaRadius = 2.5f * dm;
        alpha = 0x66;

        setColor(0xccffffff);
    }

    public void setColor(int color){
        brush = new Paint();
        brush.setStyle(Paint.Style.FILL);
        brush.setColor(color);
        brush.setAntiAlias(true);

        alphaBrush = new Paint();
        alphaBrush.setStyle(Paint.Style.FILL);
        alphaBrush.setColor(ColorMaker.getAlphaColor(alpha, color));
        alphaBrush.setAntiAlias(true);
        invalidate();
    }

    public void setSize(int size) {
        setSize(size, position);
    }

    public void setSize(int size, int position) {
        this.size = size;
        this.position = 0; // fallback
        setPosition(position);
    }

    public void setPosition(int pos) {
        if (pos < 0) {
            this.position = -1;
        } else if (pos >= size) {
            this.position = size - 1;
        } else {
            this.position = pos;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (size > 0) {
            float centerXX = getWidth() * 0.5f;
            float centerYY = getHeight() * 0.5f;

            float interval = 2 * radius + gap;
            float x = centerXX - (interval * size - gap) * 0.5f;

            for (int c = 0; c < size; c++) {
                if (c != position) {
                    canvas.drawCircle(x, centerYY, alphaRadius, alphaBrush);
                } else {
                    canvas.drawCircle(x, centerYY, radius, brush);
                }
                x += interval;
            }
        }
    }

}
