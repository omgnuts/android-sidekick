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
import android.widget.ImageView;

import com.mikimedia.android.component.PicassoHandle;

import java.util.Random;

public class ImageLoader extends PicassoHandle {

    public static final String[] dataUri;

    static {
        STORAGE_LOCATION = "/Android/data/com.mikimedia.demo";

        ONLOADING_DRAWABLE_RESID = R.mipmap.picasso_loading;
        ONERROR_DRAWABLE_RESID =  R.mipmap.picasso_noimage;

        dataUri = new String[]{
                "http://images6.backpage.com/imager/u/large/115446428/3-1.jpg",
                "http://images6.backpage.com/imager/u/large/115446386/2-2.jpg",
                "http://images5.backpage.com/imager/u/large/114891859/Shirley2.jpg",
                "http://gameanimecentral.com/wp-content/uploads/ktz/Sexy-Cute-Cosplay-Anime-Girls-HD-Wallpaper-2xl6mvz36f1a1oq1pj5r7u.png",
                "http://4.bp.blogspot.com/-TiNAm5yo5d0/UNvgfHfAbtI/AAAAAAAAX-I/4pw23wMWD78/s1600/T-ara+Sexy+Love+Pictures+Hyomin.png",
                "http://i223.photobucket.com/albums/dd203/spiketail118/momo2vd.png",
                "http://images6.backpage.com/imager/u/large/115446428/3-1.jpg",
                "http://images6.backpage.com/imager/u/large/115446386/2-2.jpg",
                "http://images5.backpage.com/imager/u/large/114891859/Shirley2.jpg",
                "http://gameanimecentral.com/wp-content/uploads/ktz/Sexy-Cute-Cosplay-Anime-Girls-HD-Wallpaper-2xl6mvz36f1a1oq1pj5r7u.png",
                "http://4.bp.blogspot.com/-TiNAm5yo5d0/UNvgfHfAbtI/AAAAAAAAX-I/4pw23wMWD78/s1600/T-ara+Sexy+Love+Pictures+Hyomin.png",
                "http://i223.photobucket.com/albums/dd203/spiketail118/momo2vd.png",
        };
    }

    private static volatile ImageLoader singleton = null;

    private ImageLoader(Context context) {
        super(context);
    }

    public static ImageLoader with(Context context) {
        if (singleton == null) {
            synchronized (ImageLoader.class) {
                if (singleton == null) {
                    singleton = new ImageLoader(context);
                }
            }
        }
        return singleton;
    }

    public void loadSampleImage(ImageView imageView) {
        Random rand = new Random();
        int n = rand.nextInt(dataUri.length - 1);
        load(dataUri[n], imageView);
    }
}
