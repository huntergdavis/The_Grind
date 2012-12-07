package com.hunterdavis.thegrind;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.view.SurfaceHolder;

public class CanvasThread extends Thread {
	private SurfaceHolder _surfaceHolder;
	private Panel _panel;
	private boolean _run = false;
	// for consistent rendering
	private long sleepTime;
	// amount of time to sleep for (in milliseconds)
	private long delay = 70;

	public CanvasThread(SurfaceHolder surfaceHolder, Panel panel,
			Context context, Handler handler) {
		_surfaceHolder = surfaceHolder;
		_panel = panel; 

	}

	public void setRunning(boolean run) {
		_run = run;
	}

	public boolean getRunning() {
		return _run;
	}

	@Override
	public void run() {

		// UPDATE
		while (_run) {
			// time before update
			long beforeTime = System.nanoTime();
			// This is where we update the game engine
			_panel.updateGameState();

			// DRAW
			Canvas c = null;
			try {
				// lock canvas so nothing else can use it
				c = _surfaceHolder.lockCanvas(null);
				synchronized (_surfaceHolder) {
					Paint paint = new Paint();
					paint.setColor(Color.WHITE);
					// clear the screen with the gray painter.
					c.drawRect(0, 0, c.getWidth(),
							c.getHeight(), paint);
					
					if(_panel.introScreenOver == false) {
						// do something?
					}
					else {
						// paint the bottom grey
						paint.setColor(Color.GRAY);
						c.drawRect(0, c.getHeight()/2, c.getWidth(),
								c.getHeight(), paint);
							
						// paint the blue ff bk on top
						paint.setColor(Color.BLUE);
						RectF myrect = new RectF();
						myrect.top = 0;
						myrect.bottom = c.getHeight() / 2;
						myrect.left = 0;
						myrect.right = c.getWidth();
						c.drawRect(myrect, paint);
						paint.setColor(Color.BLACK);
						// c.drawLine(0, c.getHeight()/2, c.getWidth(),
						// c.getHeight()/2, paint);
						c.drawLine(0, (c.getHeight() / 2) - 1, c.getWidth(),
								(c.getHeight() / 2) - 1, paint);
						c.drawLine(0, (c.getHeight() / 2) - 2, c.getWidth(),
								(c.getHeight() / 2) - 2, paint);
						// c.drawLine(0, (c.getHeight()/2)-3, c.getWidth(),
						// (c.getHeight()/2)-2, paint);

						paint.setAntiAlias(true);
						paint.setStyle(Style.STROKE);
						paint.setStrokeWidth(3);
						paint.setColor(Color.WHITE);
						// c.drawRect(0,0, c.getWidth(), c.getHeight()/2,
						// paint);
						myrect.bottom = c.getHeight() / 2 - 5;
						c.drawRoundRect(myrect, 2, 2, paint);

						// draw us a nice white top two third
						myrect.top += 3;
						myrect.right -= 3;
						myrect.left = c.getWidth() - c.getWidth() / 3;
						myrect.bottom = c.getHeight() / 2 - 2 * c.getHeight()
								/ 6;
						c.drawRoundRect(myrect, 2, 2, paint);

						// draw us a nice white bottom one third
						myrect.top = myrect.bottom + 3;
						myrect.bottom = c.getHeight() / 2 - 8;
						c.drawRoundRect(myrect, 2, 2, paint);

						// draw us a nice level square at the bottom
						paint.setStyle(Style.FILL);
						paint.setColor(Color.BLUE);
						myrect.top = c.getHeight() / 2 - c.getHeight() / 20;
						myrect.bottom = c.getHeight() / 2 + c.getHeight() / 30;
						myrect.left = c.getWidth() / 3 - c.getWidth() / 8;
						myrect.right = c.getWidth() / 3 + c.getWidth() / 12;
						c.drawRoundRect(myrect, 2, 2, paint);

						myrect.left = myrect.right + c.getWidth() / 20;
						myrect.right = myrect.left + c.getWidth() / 3;
						c.drawRoundRect(myrect, 2, 2, paint);

						paint.setStyle(Style.STROKE);
						paint.setStrokeWidth(3);
						paint.setColor(Color.WHITE);
						myrect.top = c.getHeight() / 2 - c.getHeight() / 20;
						myrect.bottom = c.getHeight() / 2 + c.getHeight() / 30;
						myrect.left = c.getWidth() / 3 - c.getWidth() / 8;
						myrect.right = c.getWidth() / 3 + c.getWidth() / 12;
						c.drawRoundRect(myrect, 2, 2, paint);

						myrect.left = myrect.right + c.getWidth() / 20;
						myrect.right = myrect.left + c.getWidth() / 3;
						c.drawRoundRect(myrect, 2, 2, paint);
					}
					// This is where we draw the game engine.
					_panel.onDraw(c);
				}
			} finally {
				// do this in a finally so that if an exception is thrown
				// during the above, we don't leave the Surface in an
				// inconsistent state
				if (c != null) {
					_surfaceHolder.unlockCanvasAndPost(c);
				}
			}

			// SLEEP
			// Sleep time. Time required to sleep to keep game consistent
			// This starts with the specified delay time (in milliseconds) then
			// subtracts from that the
			// actual time it took to update and render the game. This allows
			// our game to render smoothly.
			this.sleepTime = delay
					- ((System.nanoTime() - beforeTime) / 1000000L);

			try {
				// actual sleep code
				if (sleepTime > 0) {
					CanvasThread.sleep(sleepTime);
				}
			} catch (InterruptedException ex) {

			}
		}

	}
}