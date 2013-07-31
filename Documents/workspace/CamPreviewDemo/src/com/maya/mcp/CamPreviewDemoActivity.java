package com.maya.mcp;

import edu.clu.cp.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

public class CamPreviewDemoActivity extends Activity {
    /** Called when the activity is first created. */
	int h, w;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
     
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        h = metrics.heightPixels;
        w = metrics.widthPixels;
        Log.w("tag", "test");
        // 取得全螢幕
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay();

        setContentView(R.layout.main);
        //鎖住螢幕方向
        setRequestedOrientation(0);
        //取得畫圖的View
        ViewToDraw dtw = (ViewToDraw) findViewById(R.id.vtd);
        //產生攝影機預覽surfaceView
        CameraView cameraView = new CameraView(this, dtw, w, h);
        //把預覽的surfaceView加到名為preview的FrameLayout
        ((FrameLayout) findViewById(R.id.preview)).addView(cameraView);
    }
}