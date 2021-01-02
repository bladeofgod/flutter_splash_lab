package com.lijiaqi.flutter_splash;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import java.util.concurrent.ExecutionException;

import io.flutter.embedding.android.FlutterActivity;

public class MainActivity extends FlutterActivity {
    public MainActivity(){
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initSplashPage();
        super.onCreate(savedInstanceState);

        //addView();
    }

    public void addView(){
        SVGAImageView imageView = new SVGAImageView(getApplicationContext());

        SVGAParser parser = new SVGAParser(getApplicationContext());
        parser.decodeFromAssets("angel.svga", new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                imageView.setImageDrawable(drawable);
                imageView.startAnimation();
            }

            @Override
            public void onError() {

            }
        });
        WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = wm.getDefaultDisplay().getWidth();
        params.height = wm.getDefaultDisplay().getHeight();
        addContentView(imageView,params);
    }



    final String webUrl = "https://isparta.github.io/compare-webp/image/gif_webp/webp/1.webp";
    public void initSplashPage(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Looper.prepare();
                WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
                SVGAImageView imageView = new SVGAImageView(getApplicationContext());
                imageView.setBackgroundColor(getResources().getColor(R.color.white));
                Handler handler = new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what){
                            case 1111:
                                Log.i("glide-------", "msg");
                                imageView.setImageDrawable((SVGADrawable)msg.obj);
                                imageView.startAnimation();
                                break;
                        }
                    }
                };
                SVGAParser parser = new SVGAParser(getApplicationContext());
                parser.decodeFromAssets("angel.svga", new SVGAParser.ParseCompletion() {
                    @Override
                    public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                        SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                        Message msg = Message.obtain();
                        msg.what = 1111;
                        msg.obj = drawable;
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onError() {

                    }
                });

                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.width = wm.getDefaultDisplay().getWidth();
                params.height = wm.getDefaultDisplay().getHeight();

                wm.addView(imageView, params);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        wm.removeView(imageView);
                    }
                }, 6000);


                Looper.loop();


            }
        }).start();
    }

    public Uri getUri(){
        return new Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                .path(String.valueOf(R.drawable.lion))
                .build();
    }
}
