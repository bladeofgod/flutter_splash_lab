package com.lijiaqi.flutter_splash;

import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import io.flutter.app.FlutterApplication;

/**
 * @author LiJiaqi
 * @date 2021/1/1
 * Description:
 */
public class BaseApp extends FlutterApplication {
    @Override
    public void onCreate() {
        //init();
        Log.d("app start ----", "android  " + System.currentTimeMillis());
        Fresco.initialize(this);
        super.onCreate();



    }
    public void init(){
        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                Log.i("child thread", "init -------");
                Looper.prepare();

                WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);

                SVGAImageView imageView = new SVGAImageView(getApplicationContext());
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



                Looper.loop();


            }
        }).start();
    }
}
