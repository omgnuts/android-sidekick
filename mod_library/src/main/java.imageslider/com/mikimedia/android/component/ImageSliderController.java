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

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.View;


public class ImageSliderController {

    private final Context context;

    public ImageSliderController(Context context) {
        this.context = context;
    }

    public View onCreate() {
return null;

//        // Assigning ViewPager View and setting the adapter
//        final IndicatorView iv = (IndicatorView) findViewById(R.id.indicator);
//        iv.setSize(memo.rawImageURI.length, 0);
//
//        ViewPager pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(new ImageAdapter(getSupportFragmentManager(), memo));
//        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageSelected(int position) {
//                iv.setPosition(position);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//            }
//        });
//
//        final SwipeBackLayout swipeBackLayout = (SwipeBackLayout) findViewById(R.id.container);
//        swipeBackLayout.setDragEdge(SwipeBackLayout.DragEdge.TOP);
//        swipeBackLayout.setOnSwipeBackListener(new SwipeBackLayout.SwipeBackListener() {
//            @Override
//            public void onViewPositionChanged(float fractionAnchor, float fractionScreen, int draggingOffset) {
//                swipeBackLayout.setAlpha(1.0f - 0.16f * fractionAnchor);
//            }
//
//            @Override
//            public void onFinish() {
//            }
//        });
//
//        findViewById(R.id.close_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
    }
}
