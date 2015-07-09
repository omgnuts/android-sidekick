//package com.jattcode.common;
//
//import android.app.ActivityManager;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.drawable.Drawable;
//import android.os.Environment;
//import android.os.StatFs;
//import android.widget.ImageView;
//
//import com.davemorrissey.labs.subscaleview.ImageSource;
//import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
//import com.jattcode.mikimedia.R;
//import com.squareup.picasso.LruCache;
//import com.squareup.picasso.OkHttpDownloader;
//import com.squareup.picasso.Picasso;
//import com.squareup.picasso.Target;
//
//import java.io.File;
//
//import static android.content.Context.ACTIVITY_SERVICE;
//import static android.content.pm.ApplicationInfo.FLAG_LARGE_HEAP;
//import static android.os.Build.VERSION.SDK_INT;
//import static android.os.Build.VERSION_CODES.HONEYCOMB;
//
///**
// * Picasso singleton to get access to the cache
// */
//public class PicassoHandle {
//
//    private static final String STORAGE_LOCATION = "/Android/data/com.jattcode.mikimedia";
//
//    private static final int ONLOADING_DRAWABLE_RESID = R.mipmap.picasso_loading; //R.mipmap.image_loading;
//    private static final int ONERROR_DRAWABLE_RESID = R.mipmap.picasso_noimage;
//
//    private static final File getDiskStorageLocation() {
//        String path = Environment.getExternalStorageDirectory().toString()
//                + STORAGE_LOCATION;
//        return new File(path);
//    }
//
//    private static volatile PicassoHandle singleton = null;
//
//    private final Drawable onErrorDrawable;
//
//    private final Drawable onLoadDrawable;
//
//    private PicassoHandle(Context context) {
//        this.onErrorDrawable = context.getResources().getDrawable(ONERROR_DRAWABLE_RESID);
//        this.onLoadDrawable = context.getResources().getDrawable(ONLOADING_DRAWABLE_RESID);
//
//        this.picasso = new Picasso.Builder(context)
//                .memoryCache((manager = new CacheManager(context)).cache)
//                .downloader(OkHttpFactory.create())
//                .build();
//        this.picasso.setIndicatorsEnabled(false);
//    }
//
//    public void load(String path, Target target) {
//        picasso.load(path)
//                .placeholder(onLoadDrawable)
//                .error(onErrorDrawable)
//                .into(target);
//    }
//
//    public void load(String path, ImageView target) {
//        picasso.load(path)
//                .placeholder(onLoadDrawable)
//                .error(onErrorDrawable)
//                .into(target);
//    }
//
//    //TODO : load the image url with a callback to a callback method/class
//    // http://stackoverflow.com/questions/22143157/android-picasso-placeholder-and-error-image-styling
////    Picasso.with(context)
////            .load(imageUrl)
////    .into(myImage,  new ImageLoadedCallback(progressBar) {
////        @Override
////        public void onSuccess() {
////            if (this.progressBar != null) {
////                this.progressBar.setVisibility(View.GONE);
////            }
////        }
////    });
//
//    public static class ImageWrapper implements Target {
//
//        private final SubsamplingScaleImageView imageView;
//
//        public ImageWrapper(SubsamplingScaleImageView imageView) {
//            this.imageView = imageView;
//        }
//
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            if (this.imageView != null) {
//                this.imageView.setImage(ImageSource.bitmap(bitmap));
//            }
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {
//            if (this.imageView != null) {
//                this.imageView.setImage(ImageSource.resource(ONERROR_DRAWABLE_RESID));
//            }
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//            if (this.imageView != null) {
//                this.imageView.setImage(ImageSource.resource(ONLOADING_DRAWABLE_RESID));
//            }
//        }
//    }
//
//    public static class CacheManager {
//
//        private final LruCache cache;
//
//        private CacheManager(Context context) {
//            this.cache = new LruCache(calculateMemoryCacheSize(context));
//        }
//
//        // in bytes
//        public int maxSizeRAM() {
//            return cache.maxSize();
//        }
//
//        // in bytes
//        public int usedSizeRAM() {
//            return cache.size();
//        }
//
//        // clear mem
//        public void clearRAM() {
//            cache.clear();
//        }
//
//        public long usedSizeDISK() {
//            return sizeOf(getDiskStorageLocation());
//        }
//
//        public boolean clearDISK() {
//            return delete(getDiskStorageLocation());
//        }
//
//        private static int calculateMemoryCacheSize(Context context) {
//            ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
//            boolean largeHeap = (context.getApplicationInfo().flags & FLAG_LARGE_HEAP) != 0;
//            int memoryClass;
//            if (largeHeap && SDK_INT >= HONEYCOMB) {
//                memoryClass = am.getLargeMemoryClass();
//            } else {
//                memoryClass = am.getMemoryClass();
//            }
//            // Target ~15% of the available heap.
//            return 1024 * 1024 * memoryClass / 7;
//        }
//
//        private static long sizeOf(File directory) {
//            if (directory.exists()) {
//                final File[] files = directory.listFiles();
//                if (files == null) {  // null if security restricted
//                    return 0L;
//                }
//                long result = 0;
//                for (int i = 0; i < files.length; i++) {
//                    // Recursive call if it's a directory
//                    if (files[i].isDirectory()) {
//                        result += sizeOf(files[i]);
//                    } else {
//                        // Sum the file size in bytes
//                        result += files[i].length();
//                    }
//                }
//                return result; // return the file size
//            }
//            return 0;
//        }
//
//        private static boolean delete(File directory) {
//            if (directory.exists()) {
//                final File[] files = directory.listFiles();
//                if (files == null) {  // null if security restricted
//                    return false;
//                }
//
//                boolean success;
//                for (int i = 0; i < files.length; i++) {
//                    // Recursive call if it's a directory
//                    if (files[i].isDirectory()) {
//                        success = delete(files[i]);
//                    } else {
//                        success = files[i].delete();
//                    }
//                    if (!success) return false; // stop the whole process
//                }
//
//                return true;
//            }
//            return false;
//        }
//
//    }
//
//    // to create the cache
//    private static final class OkHttpFactory {
//
//        private static final int MIN_DISK_CACHE_SIZE = 5 * 1024 * 1024; // 5MB
//        private static final int MAX_DISK_CACHE_SIZE = 50 * 1024 * 1024; // 50MB
//
//        private static OkHttpDownloader create() {
//            File cacheDir = getDiskStorageLocation();
//            return new OkHttpDownloader(cacheDir, calculateDiskCacheSize(cacheDir));
//        }
//
//        private static long calculateDiskCacheSize(File dir) {
//            long size = MIN_DISK_CACHE_SIZE;
//
//            try {
//                StatFs statFs = new StatFs(dir.getAbsolutePath());
//                long available = ((long) statFs.getBlockCount()) * statFs.getBlockSize();
//                // Target 2% of the total space.
//                size = available / 50;
//            } catch (IllegalArgumentException ignored) {
//            }
//
//            // Bound inside min/max size for disk cache.
//            return Math.max(Math.min(size, MAX_DISK_CACHE_SIZE), MIN_DISK_CACHE_SIZE);
//        }
//
////        EXAMPLE NEED PASSWORD AUTHENTICATION ETC IN HEADERS
////        REF: http://stackoverflow.com/questions/24981469/custom-downloader-using-picasso
////
////        public static Picasso getImageLoader(Context ctx) {
////            Picasso.Builder builder = new Picasso.Builder(ctx);
////
////            builder.downloader(new UrlConnectionDownloader(ctx) {
////                @Override
////                protected HttpURLConnection openConnection(Uri uri) throws IOException {
////                    HttpURLConnection connection = super.openConnection(uri);
////                    connection.setRequestProperty("X-HEADER", "VAL");
////                    return connection;
////                }
////            });
////
////            return builder.build();
////        }
////
////        OkHttpClient picassoClient = OkHttpClient();
////
////        picassoClient.interceptors().add(new Interceptor() {
////            @Override
////            public Response intercept(Chain chain) throws IOException {
////                Request newRequest = chain.request().newBuilder()
////                        .addHeader("X-HEADER", "VAL")
////                        .build();
////                return chain.proceed(newRequest);
////            }
////        });
////
////        new Picasso.Builder(context).downloader(new OkHttpDownloader(picassoClient)).build();
//
//
//
//    }
//
//    private final Picasso picasso;
//
//    private final CacheManager manager;
//
//    public CacheManager getCacheManager() {
//        return this.manager;
//    }
//
//    public static PicassoHandle get(Context context) {
//        if (singleton == null) {
//            synchronized (PicassoHandle.class) {
//                if (singleton == null) {
//                    singleton = new PicassoHandle(context);
//                }
//            }
//        }
//        return singleton;
//    }
//}
