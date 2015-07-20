
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

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.StatFs;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.squareup.picasso.Transformation;

import java.io.File;

import javax.xml.transform.Transformer;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.HONEYCOMB;

/**
 * Picasso singleton to get access to the cache
 */
public abstract class PicassoHandle {

    protected static String STORAGE_LOCATION;
    protected static int ONLOADING_DRAWABLE_RESID;
    protected static int ONERROR_DRAWABLE_RESID;

    private static final File getDiskStorageLocation() {
        String path = Environment.getExternalStorageDirectory().toString()
                + STORAGE_LOCATION;
        return new File(path);
    }

    private final Picasso picasso;

    private final BitmapDrawable onLoadDrawable;

    private final BitmapDrawable onErrorDrawable;

    public BitmapDrawable getOnErrorDrawable() {
        return onErrorDrawable;
    }

    private static void checkPermissionsOrThrowException(Context context) {
        String[] permissions = new String[] {
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.INTERNET,
                android.Manifest.permission.ACCESS_NETWORK_STATE
        };

        for (String permission : permissions) {
            int result = context.checkCallingOrSelfPermission(permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                throw new RuntimeException(
                        "Error: PicassoHandle permissions required: " + permission);
            }
        }
    }

    protected PicassoHandle(Context context) {
        checkPermissionsOrThrowException(context);

        this.onErrorDrawable = (BitmapDrawable) ContextCompat.getDrawable(context, ONERROR_DRAWABLE_RESID);
        this.onLoadDrawable = (BitmapDrawable) ContextCompat.getDrawable(context, ONLOADING_DRAWABLE_RESID);

        this.picasso = new Picasso.Builder(context)
                .memoryCache((manager = new CacheManager(context)).cache)
                .downloader(OkHttpFactory.create())
                .build();
        this.picasso.setIndicatorsEnabled(false);
    }

    private final CacheManager manager;

    public CacheManager getCacheManager() {
        return this.manager;
    }

    public void load(String path, Target target, Transformation transformer) {
        picasso.load(path)
                .placeholder(onLoadDrawable)
                .error(onErrorDrawable)
                .transform(transformer)
                .into(target);
    }

    public void load(String path, Target target, Point size) {
        picasso.load(path)
                .placeholder(onLoadDrawable)
                .error(onErrorDrawable)
                .resize(size.x, size.y)
                .onlyScaleDown()
                .into(target);
    }

    public void load(String path, Target target) {
        picasso.load(path)
                .placeholder(onLoadDrawable)
                .error(onErrorDrawable)
                .into(target);
    }

    public void load(String path, ImageView target) {
        picasso.load(path)
                .placeholder(onLoadDrawable)
                .error(onErrorDrawable)
                .into(target);
    }

    //TODO : load the image url with a callback to a callback method/class
    // http://stackoverflow.com/questions/22143157/android-picasso-placeholder-and-error-image-styling
//    Picasso.with(context)
//            .load(imageUrl)
//    .into(myImage,  new ImageLoadedCallback(progressBar) {
//        @Override
//        public void onSuccess() {
//            if (this.progressBar != null) {
//                this.progressBar.setVisibility(View.GONE);
//            }
//        }
//    });

    public static final class CacheManager {

        private final LruCache cache;

        private CacheManager(Context context) {
            this.cache = new LruCache(calculateMemoryCacheSize(context));
        }

        // in bytes
        public int maxSizeRAM() {
            return cache.maxSize();
        }

        // in bytes
        public int usedSizeRAM() {
            return cache.size();
        }

        // clear mem
        public void clearRAM() {
            cache.clear();
        }

        public long usedSizeDISK() {
            return sizeOf(getDiskStorageLocation());
        }

        public boolean clearDISK() {
            return delete(getDiskStorageLocation());
        }

        private static int calculateMemoryCacheSize(Context context) {
            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
            boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
            int memoryClass;
            if (largeHeap && SDK_INT >= HONEYCOMB) {
                memoryClass = am.getLargeMemoryClass();
            } else {
                memoryClass = am.getMemoryClass();
            }
            // Target ~15% of the available heap.
            return 1024 * 1024 * memoryClass / 7;
        }

        private static long sizeOf(File directory) {
            if (directory.exists()) {
                final File[] files = directory.listFiles();
                if (files == null) {  // null if security restricted
                    return 0L;
                }
                long result = 0;
                for (int i = 0; i < files.length; i++) {
                    // Recursive call if it's a directory
                    if (files[i].isDirectory()) {
                        result += sizeOf(files[i]);
                    } else {
                        // Sum the file size in bytes
                        result += files[i].length();
                    }
                }
                return result; // return the file size
            }
            return 0;
        }

        private static boolean delete(File directory) {
            if (directory.exists()) {
                final File[] files = directory.listFiles();
                if (files == null) {  // null if security restricted
                    return false;
                }

                boolean success;
                for (int i = 0; i < files.length; i++) {
                    // Recursive call if it's a directory
                    if (files[i].isDirectory()) {
                        success = delete(files[i]);
                    } else {
                        success = files[i].delete();
                    }
                    if (!success) return false; // stop the whole process
                }

                return true;
            }
            return false;
        }

    }

    // to create the cache
    private static final class OkHttpFactory {

        private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
        private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB

        private static OkHttpDownloader create() {
            File cacheDir = getDiskStorageLocation();
            return new OkHttpDownloader(cacheDir, calculateDiskCacheSize(cacheDir));
        }

        private static long calculateDiskCacheSize(File dir) {
            long size = MIN_DISK_CACHE_SIZE;

            try {
                StatFs statFs = new StatFs(dir.getAbsolutePath());
                long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
                // Target 2% of the total space.
                size = available / 50;
            } catch (IllegalArgumentException ignored) {
            }

            // Bound inside min/max size for disk cache.
            return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
        }

//        EXAMPLE NEED PASSWORD AUTHENTICATION ETC IN HEADERS
//        REF: http://stackoverflow.com/questions/24981469/custom-downloader-using-picasso
//
//        public static Picasso getImageLoader(Context ctx) {
//            Picasso.Builder builder = new Picasso.Builder(ctx);
//
//            builder.downloader(new UrlConnectionDownloader(ctx) {
//                @Override
//                protected HttpURLConnection openConnection(Uri uri) throws IOException {
//                    HttpURLConnection connection = super.openConnection(uri);
//                    connection.setRequestProperty("X-HEADER", "VAL");
//                    return connection;
//                }
//            });
//
//            return builder.build();
//        }
//
//        OkHttpClient picassoClient = OkHttpClient();
//
//        picassoClient.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request newRequest = chain.request().newBuilder()
//                        .addHeader("X-HEADER", "VAL")
//                        .build();
//                return chain.proceed(newRequest);
//            }
//        });
//
//        new Picasso.Builder(context).downloader(new OkHttpDownloader(picassoClient)).build();


    }


}
