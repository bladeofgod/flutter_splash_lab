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
        init();
        super.onCreate(savedInstanceState);
    }
    final String webUrl = "https://isparta.github.io/compare-webp/image/gif_webp/webp/1.webp";
    public void init(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Log.i("child thread", "init -------");
                Looper.prepare();

                WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
//                DraweeController controller = Fresco.newDraweeControllerBuilder()
//                        .setUri(Uri.parse(webUrl))
//                        .setAutoPlayAnimations(true)
//                        .build();

//
//                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(MainActivity.this);
//                simpleDraweeView.setController(controller);
                //simpleDraweeView.setImageResource(R.drawable.lion);
//                ImageView imageView = new ImageView(MainActivity.this);
//                imageView.setBackgroundColor(getResources().getColor(R.color.red));
                SVGAImageView imageView = new SVGAImageView(MainActivity.this);
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
                parser.decodeFromAssets("Goddess.svga", new SVGAParser.ParseCompletion() {
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

//                Bitmap bitmap;
//                RequestOptions options = new RequestOptions();
//                options.diskCacheStrategy(DiskCacheStrategy.ALL);
//                final GifDrawable[] drawable = new GifDrawable[1];

//
//                try {
//                      Glide.with(MainActivity.this)
//                            .asGif()
//                            .diskCacheStrategy(DiskCacheStrategy.ALL)
//                            .load(R.drawable.lion)
//                             .listener(new RequestListener<GifDrawable>() {
//                                 @Override
//                                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<GifDrawable> target, boolean isFirstResource) {
//                                     return false;
//                                 }
//
//                                 @Override
//                                 public boolean onResourceReady(GifDrawable resource, Object model, Target<GifDrawable> target, DataSource dataSource, boolean isFirstResource) {
//                                     Log.i("--------", "ready");
//                                     if(!resource.isRunning()){
//                                         resource.start();
//                                     }
//
////                                     Message msg = Message.obtain();
////                                     msg.what = 1111;
////                                     handler.sendMessage(msg);
//                                     imageView.setImageDrawable(getDrawable(R.drawable.lion));
//                                     //imageView.setImageDrawable(resource.getCurrent());
//                                     return true;
//                                 }
//                             }).submit().get();
////                             .listener(new RequestListener<Bitmap>() {
////                                 @Override
////                                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
////                                     return false;
////                                 }
////
////                                 @Override
////                                 public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
////
////                                     imageView.setImageBitmap(resource);
////                                     return false;
////                                 }
////                             })
//
//                     //imageView.setImageBitmap(bitmap);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }

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
