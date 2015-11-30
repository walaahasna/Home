package com.example.jit.home;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
private static VolleySingleton sInstance = null;
private ImageLoader mImageLoader;
private RequestQueue mRequestQueue;

private VolleySingleton(Context context) {
    mRequestQueue = Volley.newRequestQueue(context);
    mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

        private LruCache<String, Bitmap> cache = new LruCache<>((int) (Runtime.getRuntime().maxMemory() / 1024) / 8);

        @Override
        public Bitmap getBitmap(String url) {
            return cache.get(url);
        }

        @Override
        public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
        }
    });
}
    private VolleySingleton(){
        mRequestQueue = Volley.newRequestQueue(MyApplication.getAppContext());
        mImageLoader = new ImageLoader(this.mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(10);
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleySingleton getInstance(){
        if(sInstance == null){
           sInstance = new VolleySingleton();
        }
        return sInstance;
    }

public static VolleySingleton getInstance(Context c) {
    if (sInstance == null) {
        sInstance = new VolleySingleton(c);
    }
    return sInstance;
}

public RequestQueue getRequestQueue() {
    return this.mRequestQueue;
}
public ImageLoader getImageLoader(){
    return this.mImageLoader;
}}