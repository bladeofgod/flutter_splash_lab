package com.lijiaqi.flutter_splash;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.ImageDecoder;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.animated.base.AbstractAnimatedDrawable;

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
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(webUrl))
                        .setAutoPlayAnimations(true)
                        .build();


                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(MainActivity.this);
                simpleDraweeView.setController(controller);
                //simpleDraweeView.setImageResource(R.drawable.lion);
//                Button btn5 = new Button(MainActivity.this);
//                btn5.setText("123123");
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.width = wm.getDefaultDisplay().getWidth();
                params.height = wm.getDefaultDisplay().getHeight();

                wm.addView(simpleDraweeView, params);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        wm.removeView(simpleDraweeView);
//                    }
//                }, 2000);
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
