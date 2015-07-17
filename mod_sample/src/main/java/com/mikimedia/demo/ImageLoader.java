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

package com.mikimedia.demo;

import android.content.Context;

import com.mikimedia.android.component.PicassoHandle;

public class ImageLoader extends PicassoHandle {

    static {
        STORAGE_LOCATION = "/Android/data/com.mikimedia.demo";

        ONLOADING_DRAWABLE_RESID = R.mipmap.picasso_loading;
        ONERROR_DRAWABLE_RESID =  R.mipmap.picasso_noimage;
    }

    private static volatile ImageLoader singleton = null;

    private ImageLoader(Context context) {
        super(context);

    }

    public static ImageLoader get(Context context) {
        if (singleton == null) {
            synchronized (ImageLoader.class) {
                if (singleton == null) {
                    singleton = new ImageLoader(context);
                }
            }
        }
        return singleton;
    }
}
