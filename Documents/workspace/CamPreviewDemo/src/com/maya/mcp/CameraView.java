package com.maya.mcp;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;



public class CameraView extends SurfaceView implements SurfaceHolder.Callback{

	public Camera mycamera;
	List<Camera.Size> cameraSize;
	
	private SurfaceHolder mHolder;	
	
	public ViewToDraw vtd;
	
	int pickedH, pickedW;
	int defaultH, defaultW;
	

	
	public CameraView(Context context, ViewToDraw _vtd, int width, int height) {
		super(context);
		// TODO Auto-generated constructor stub
		
		mHolder = getHolder(); 
		mHolder.addCallback(this); 
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
		
		vtd = _vtd;
		
		defaultH = height;
		defaultW = width;
		
		Log.w("tag",defaultW+ " "+defaultH);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) { 
		int i;
		
		mycamera = Camera.open(); 
		cameraSize = mycamera.getParameters().getSupportedPreviewSizes();
		if(cameraSize != null){
			// pick resolution
			pickedH = defaultH;
			pickedW = defaultW;
			
			for(i=0;i<cameraSize.size();i++){
				if(cameraSize.get(i).width < defaultW){
					break;
				}else{
					pickedH = cameraSize.get(i).height;
					pickedW = cameraSize.get(i).width;
				}
			}


		}else{
			Log.e("tag","null");
		};

		Log.w("tag",pickedW+ " "+pickedH);
		try {
			mycamera.setPreviewDisplay(holder);  
		} catch (IOException e) { 
			e.printStackTrace();
		}
 	}


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Camera.Parameters parameters = mycamera.getParameters();
		parameters.setPreviewSize(pickedW, pickedH);
		mycamera.setParameters(parameters);

		//create buffer
        PixelFormat p = new PixelFormat();
        PixelFormat.getPixelFormatInfo(parameters.getPreviewFormat(),p);
        int bufSize = (pickedW*pickedH*p.bitsPerPixel)/8;
        
        //add buffers
        byte[] buffer = new byte[bufSize];
        mycamera.addCallbackBuffer(buffer);                            
        buffer = new byte[bufSize];
        mycamera.addCallbackBuffer(buffer);
        buffer = new byte[bufSize];
        mycamera.addCallbackBuffer(buffer);
        
        mycamera.setPreviewCallbackWithBuffer(new PreviewCallback() {
			public void onPreviewFrame(byte[] data, Camera camera) { 
                vtd.putImage(data);
                vtd.cameraSet();
                vtd.imgHeight = pickedH;
                vtd.imgWidth = pickedW;
                //更新畫布 (call onDraw())
                vtd.invalidate();
                //把buffer丟回給callback重新利用
				mycamera.addCallbackBuffer(data);
			}
		});
		mycamera.startPreview();
	}


	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		mycamera.setPreviewCallback(null);
		mycamera.release();
		mycamera = null;
	}



}
