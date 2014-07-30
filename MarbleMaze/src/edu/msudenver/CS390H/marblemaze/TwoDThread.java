package edu.msudenver.CS390H.marblemaze;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class TwoDThread extends Thread {
	boolean mRun;
	Canvas mcanvas;
	SurfaceHolder surfaceHolder;
	Context context;
	TwoDDrawFrag msurfacePanel;

	public TwoDThread(SurfaceHolder sholder, Context ctx, TwoDDrawFrag spanel) {
		surfaceHolder = sholder;
		context = ctx;
		mRun = false;
		msurfacePanel = spanel;
	}

	void setRunning(boolean bRun) {
		mRun = bRun;
	}

	@Override
	public void run() {
		super.run();

		while (mRun) {
			mcanvas = surfaceHolder.lockCanvas();

			if (mcanvas != null) {
				msurfacePanel.doDraw(mcanvas);
				surfaceHolder.unlockCanvasAndPost(mcanvas);
			}
		}

	}

}