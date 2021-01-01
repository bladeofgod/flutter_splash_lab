package com.lijiaqi.flutter_splash;

import android.app.Application;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import io.flutter.app.FlutterApplication;

/**
 * @author LiJiaqi
 * @date 2021/1/1
 * Description:
 */
public class BaseApp extends FlutterApplication {
    @Override
    public void onCreate() {

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

                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(BaseApp.this);
                simpleDraweeView.setImageResource(R.drawable.lion);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.width = wm.getDefaultDisplay().getWidth();
                params.height = wm.getDefaultDisplay().getHeight();

                wm.addView(simpleDraweeView, params);
                Looper.loop();


            }
        }).start();
    }
}
