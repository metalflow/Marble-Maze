package edu.msudenver.CS390H.marblemaze;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class TwoDDrawFrag extends GLSurfaceView implements
		SurfaceHolder.Callback {

	public final boolean isImplemented = false;
	Bitmap mbitmap;
	TwoDThread mythread;
	Context context;
	float accelX = 0;
	float accelY = 0;
	float accelZ = 0;
	float velX = 0;
	float velY = 0;
	float velZ = 0;
	float posX = 0;
	float posY = 0;
	float posZ = 0;
	float radius = 5;
	Paint marblePaint = new Paint();
	Paint goalPaint = new Paint();
	SurfaceHolder holder;
	int level = 0;
	int goalX;
	int goalY;

	public TwoDDrawFrag(Context ctx, AttributeSet attrSet) {
		super(ctx, attrSet);

		context = ctx;
		marblePaint.setColor(Color.BLUE);
		holder = getHolder();
		holder.addCallback(this);
		
		Log.i("TwoDDrawFrag_constructor", "constructor finished");
	}
	
	public TwoDDrawFrag(Context ctx) {
		super(ctx);

		context = ctx;
		marblePaint.setColor(Color.BLUE);
		goalPaint.setColor(Color.YELLOW);
		holder = getHolder();
		holder.addCallback(this);
		
		Log.i("TwoDDrawFrag_constructor", "constructor finished");
		//switch (level)
	}

	void doDraw(Canvas canvas) {
		goalX = canvas.getWidth()/2;
		goalY = canvas.getHeight()/2;
		
		posX= posX-((1/2)*(accelX*accelX))-accelX;
		posY= posY+((1/2)*(accelY*accelY))+accelY;
		//posZ= posZ-((1/2)*(accelZ*accelZ))-accelZ;
		posX = Math.min(posX, canvas.getWidth());
		posX = Math.max(posX, 0);
		posY = Math.min(posY, canvas.getHeight());
		posY = Math.max(posY, 0);
		posX = Math.min(posX, canvas.getWidth());
		posX = Math.max(posX, 0);
		posZ = Math.min((posZ*2), Math.min(canvas.getHeight(), canvas.getWidth()));
		posZ = Math.max(posZ, 0);
		//Log.i("TwoDDrawFrag_doDraw", "accelZ: " + accelZ + " posZ: " + posZ);
		//Log.i("TwoDDrawFrag_doDraw", "posX: " + posX + " posY: " + posY + " posZ: " + posZ);
		canvas.drawColor(Color.GREEN);
		canvas.drawCircle(goalX, goalY, radius, goalPaint);
		
		canvas.drawCircle(posX, posY, radius + posZ, marblePaint);

		if (Math.pow((posX - goalX),2) + Math.pow((posY - goalY),2) <= Math.pow(radius,2)){
			surfaceDestroyed(holder);
		}
		
		//Log.i("TwoDDrawFrag_doDraw", "doDraw finished");
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mythread = new TwoDThread(holder, context, this);
		mythread.setRunning(true);
		mythread.start();
		
		Log.i("TwoDDrawFrag_surfaceCreated", "surfaceCreated finished");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mythread.setRunning(false);
		boolean retry = true;
		while (retry) {
			try {
				mythread.join();
				retry = false;
			} catch (Exception e) {
				Log.v("Exception Occured", e.getMessage());
			}
		}

		Log.i("TwoDDrawFrag_surfaceDestroyed", "surfaceDestroyed finished");
	}

	public void updateGravity(float x, float y,float z){
		accelX=x;
		accelY=y;
		accelZ=z;
	}
}
