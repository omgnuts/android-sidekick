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

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A page adapter which works with a large data set by reusing views.
 */
public abstract class ObjectPageAdapter extends PagerAdapter {

    // Views that can be reused.
    private final List<View> recycler = new ArrayList<View>();

    @Override
    public abstract int getCount();

    public abstract View getView(int position, View view, ViewGroup parent);

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        View view = recycler.isEmpty() ? null : recycler.remove(0);
        view = getView(position, view, parent);
        parent.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        if (view != null) {
            recycler.add(view);
            container.removeView(view);
        }
    }

    @Override
    public boolean isViewFromObject(View v, Object obj) {
        return v == obj;
    }

    @Override
    public void notifyDataSetChanged() {
        recycler.clear();
        super.notifyDataSetChanged();
    }
}