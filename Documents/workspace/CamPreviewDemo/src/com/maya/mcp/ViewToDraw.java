package com.maya.mcp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

public class ViewToDraw extends View{

	public byte[] image;
	
	public boolean isCameraSet = false;
	
	public int imgWidth, imgHeight;
	
	private Paint whitePaint;
	private Paint blackPaint;
	
	public ViewToDraw(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		
		whitePaint = new Paint();
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStrokeWidth(3.0f);
		
		blackPaint = new Paint();
		blackPaint.setColor(Color.BLACK);
		blackPaint.setStrokeWidth(3.0f);
	}
	
	public void cameraSet(){
		isCameraSet = true;
	}
	
	public void putImage(byte[] img){
		image = img;
	}
	
	
	@Override
	protected void onDraw(Canvas canvas){
	        int i, j;
	        Log.w("tag",isCameraSet+"");
	        //將預覽圖資料與畫布做長寬縮放比運算
	        float hscale = (float)canvas.getHeight()/imgHeight;
	        float wscale = (float)canvas.getWidth()/imgWidth;
	        if(isCameraSet){
	                for(i=0;i<imgWidth;i+=8){
	                        for(j=0;j<imgHeight;j+=8){
	                                if(getBoolean(i,j)){
	                                        canvas.drawPoint(i*wscale, j*hscale,
	                                                         whitePaint);
	                                }else{
	                                        canvas.drawPoint(i*wscale, j*hscale,
	                                                         blackPaint);
	                                }
	                        }
	                }
	        }
	}
	
	
	private boolean getBoolean(int x, int y){
        int l;
        l = (0xff & ((int) image[x+y*imgWidth])) - 16;
        if (l > 128){
                return true;
        }else{
                return false;
        }
	}
	
	

}
