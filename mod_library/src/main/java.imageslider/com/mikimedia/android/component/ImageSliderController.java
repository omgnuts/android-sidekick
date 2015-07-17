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

package com.mikimedia.android.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mikimedia.android.R;
import com.mikimedia.android.view.IndicatorView;
import com.mikimedia.android.view.ObjectPageAdapter;
import com.mikimedia.android.view.SubSamplingTargetView;
import com.mikimedia.android.view.SubSamplingTargetView.SimpleOnImageEventListener;
import com.mikimedia.android.view.SwipeBackLayout;
import com.squareup.picasso.Target;


public class ImageSliderController {

    public interface ImageDataAdapter {
        int size();
        void bindView(int position, Target target);
    }

    private final Activity activity;

    private SwipeBackLayout container;

    private BitmapDrawable errorDrawble = null;

    public ImageSliderController(AppCompatActivity activity) {
        if (!(activity instanceof AppCompatActivity)) {
            throw new IllegalStateException(
                    "Can only attach ImageSlider to AppCompatActivity");
        }

        this.activity = activity;
    }

    public View onCreate() {
        final LayoutInflater inflater = LayoutInflater.from(activity);

        container = (SwipeBackLayout) inflater.inflate(R.layout.imageslider_container, null);
        container.setDragEdge(SwipeBackLayout.DragEdge.TOP);
        container.setOnSwipeBackListener(new SwipeBackLayout.SwipeBackListener() {
            @Override
            public void onViewPositionChanged(float fractionAnchor, float fractionScreen, int draggingOffset) {
                container.setAlpha(1.0f - 0.16f * fractionAnchor);
            }

            @Override
            public void onFinish() {
            }
        });

        container.findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
            }
        });

        return container;
    }

    public void init(ImageDataAdapter dataAdapter, BitmapDrawable errorDrawable) {
        this.errorDrawble = errorDrawable;

        // Assigning ViewPager View and setting the adapter
        final IndicatorView iv = (IndicatorView) container.findViewById(R.id.indicator);
        iv.setVisibility(View.VISIBLE);
        iv.setSize(dataAdapter.size(), 0);

        final ViewPager pager = (ViewPager) container.findViewById(R.id.pager);
        pager.setAdapter(new ImagePageAdapter(activity, dataAdapter));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                iv.setPosition(position);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }


    private class ImagePageAdapter extends ObjectPageAdapter {

        private class ViewHolder extends SimpleOnImageEventListener {
            SubSamplingTargetView target;

            ViewHolder(View itemView) {
                target = (SubSamplingTargetView) itemView.findViewById(R.id.image_view);
                target.setOnImageEventListener(this);
                target.setOnTouchListener(onTouchListener);
                itemView.setTag(this);
            }

            @Override
            public void onPreviewLoadError(Exception e) {
                if (errorDrawble != null) {
                    target.onBitmapFailed(errorDrawble);
                }
            }

            @Override
            public void onImageLoadError(Exception e) {
                if (errorDrawble != null) {
                    target.onBitmapFailed(errorDrawble);
                }
            }

            @Override
            public void onTileLoadError(Exception e) {
                if (errorDrawble != null) {
                    target.onBitmapFailed(errorDrawble);
                }
            }
        }

        private final ImageDataAdapter dataAdapter;

        private final LayoutInflater inflater;

        final View.OnTouchListener onTouchListener;

        private ImagePageAdapter(final Context context, ImageDataAdapter dataAdapter) {
            this.dataAdapter = dataAdapter;
            this.inflater = LayoutInflater.from(context);

            onTouchListener = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
                final GestureDetector gestureDetector = new GestureDetector(
                        context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapConfirmed(MotionEvent e) {
//                    if (imageView.isReady()) {
//                        PointF sCoord = imageView.viewToSourceCoord(e.getX(), e.getY());
//                        // ...
//                    }
                        System.out.println("...... do something");
                        return true;
                    }

                    public void onLongPress(MotionEvent e) {

                    }

                });
            };
        }

        @Override
        public int getCount() {
            return dataAdapter.size();
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            ViewHolder holder;
            if (view == null) {
                view = inflater.inflate(R.layout.imageslider_imageview, null);
                holder = new ViewHolder(view);
            } else {
                holder = (ViewHolder) view.getTag();
            }

            dataAdapter.bindView(position, holder.target);

            return view;
        }

    }

}
