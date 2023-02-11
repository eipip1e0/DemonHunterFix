package com.bicore.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.bicore.BicoreActivity;
import com.bicore.NativeFuntion;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class BitmapSurfaceView extends View implements NativeFuntion.RenderEventListener {
    private static final String TAG = "BitmapSurfaceView";

    static Timer timer = new Timer(true);
    private final int byteperpixel;
    Queue<MotionEvent> eventQueue = new LinkedList();
    int[] lx = new int[8];
    int[] ly = new int[8];
    private long mStartTime;
    private Bitmap surfaceBitmap;
    private ByteBuffer surfaceBuffer;

    public native void FillBuffer(byte[] bArr, int i, int i2, int i3, long j);

    public native void FirstInit();

    public native int GetGameHeight();

    public native int GetGameWidth();

    public BitmapSurfaceView(Context context) {
        super(context);
        Log.d(TAG, "FirstInit");
        FirstInit();
        Log.d(TAG, "GetGameWidth before = " + BicoreActivity.iContentsWidth);
        BicoreActivity.iContentsWidth = GetGameWidth();
        Log.d(TAG, "GetGameWidth after = " + BicoreActivity.iContentsWidth);
        Log.d(TAG, "GetGameHeight before" + BicoreActivity.iContentsHeight);
        BicoreActivity.iContentsHeight = GetGameHeight();
        Log.d(TAG, "GetGameHeight after" + BicoreActivity.iContentsHeight);
        Log.d(TAG, "BitmapSurfaceView begin");
        this.byteperpixel = 2;
        this.surfaceBitmap = Bitmap.createBitmap(BicoreActivity.iContentsWidth, BicoreActivity.iContentsHeight, Bitmap.Config.RGB_565);
        this.surfaceBuffer = ByteBuffer.allocate(BicoreActivity.iContentsWidth * BicoreActivity.iContentsHeight * this.byteperpixel);
        this.mStartTime = System.currentTimeMillis();
        NativeFuntion.setRenderEventListener(this);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        FillBuffer(this.surfaceBuffer.array(), BicoreActivity.iContentsWidth, BicoreActivity.iContentsHeight, this.byteperpixel * BicoreActivity.iContentsWidth, System.currentTimeMillis() - this.mStartTime);
        Bitmap bitmap = this.surfaceBitmap;
        ByteBuffer byteBuffer = this.surfaceBuffer;
        byteBuffer.rewind();
        bitmap.copyPixelsFromBuffer(byteBuffer);
        canvas.drawBitmap(this.surfaceBitmap, (Rect) null, new Rect(0, 0, BicoreActivity.iScreenWidth, BicoreActivity.iScreenHeight), new Paint(2));
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.eventQueue.add(MotionEvent.obtain(event));
        /* class com.bicore.graphic.BitmapSurfaceView.AnonymousClass1 */
        BicoreActivity.GetActivity().runOnUiThread(() -> {
            MotionEvent copyEvent = BitmapSurfaceView.this.eventQueue.poll();
            try {
                if (copyEvent != null) {
                    int actionEvent = copyEvent.getAction();
                    int actionType = actionEvent & 255;
                    if (actionType == 0) {
                        int iX = (((int) copyEvent.getX(0)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth;
                        int iY = (((int) copyEvent.getY(0)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight;
                        int actionPointerID = copyEvent.getPointerId(0);
                        NativeFuntion.nativeHandleEvent(23, iX, iY, iX, iY);
                        BitmapSurfaceView.this.lx[actionPointerID] = iX;
                        BitmapSurfaceView.this.ly[actionPointerID] = iY;
                    } else if (actionType == 1) {
                        int actionPointerID2 = copyEvent.getPointerId(0);
                        NativeFuntion.nativeHandleEvent(24, (((int) copyEvent.getX(0)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth, (((int) copyEvent.getY(0)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight, BitmapSurfaceView.this.lx[actionPointerID2], BitmapSurfaceView.this.ly[actionPointerID2]);
                        BitmapSurfaceView.this.lx[actionPointerID2] = 0;
                        BitmapSurfaceView.this.ly[actionPointerID2] = 0;
                    } else if (actionType == 5) {
                        int actionPointerID3 = (actionEvent & 65280) >> 8;
                        int actionPointerIndex = copyEvent.findPointerIndex(actionPointerID3);
                        int iX2 = (((int) copyEvent.getX(actionPointerIndex)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth;
                        int iY2 = (((int) copyEvent.getY(actionPointerIndex)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight;
                        NativeFuntion.nativeHandleEvent(23, iX2, iY2, iX2, iY2);
                        BitmapSurfaceView.this.lx[actionPointerID3] = iX2;
                        BitmapSurfaceView.this.ly[actionPointerID3] = iY2;
                    } else if (actionType == 6) {
                        int actionPointerID4 = (actionEvent & 65280) >> 8;
                        int actionPointerIndex2 = copyEvent.findPointerIndex(actionPointerID4);
                        if (actionPointerIndex2 < 0) {
                            int actionPointerCount = copyEvent.getPointerCount();
                            int i = 0;
                            while (true) {
                                if (i >= actionPointerCount) {
                                    break;
                                } else if (copyEvent.getPointerId(i) == actionPointerID4) {
                                    actionPointerIndex2 = i;
                                    break;
                                } else {
                                    i++;
                                }
                            }
                        }
                        if (actionPointerIndex2 < 0) {
                            actionPointerIndex2 = 0;
                        }
                        NativeFuntion.nativeHandleEvent(24, (((int) copyEvent.getX(actionPointerIndex2)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth, (((int) copyEvent.getY(actionPointerIndex2)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight, BitmapSurfaceView.this.lx[actionPointerID4], BitmapSurfaceView.this.ly[actionPointerID4]);
                        BitmapSurfaceView.this.lx[actionPointerID4] = 0;
                        BitmapSurfaceView.this.ly[actionPointerID4] = 0;
                    } else if (actionType == 2) {
                        int actionPointerCount2 = copyEvent.getPointerCount();
                        for (int i2 = 0; i2 < actionPointerCount2; i2++) {
                            int iId = copyEvent.getPointerId(i2);
                            int iX3 = (((int) copyEvent.getX(iId)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth;
                            int iY3 = (((int) copyEvent.getY(iId)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight;
                            if (!(iX3 == BitmapSurfaceView.this.lx[iId] || iY3 == BitmapSurfaceView.this.ly[iId])) {
                                NativeFuntion.nativeHandleEvent(26, iX3, iY3, BitmapSurfaceView.this.lx[iId], BitmapSurfaceView.this.ly[iId]);
                                BitmapSurfaceView.this.lx[iId] = iX3;
                                BitmapSurfaceView.this.ly[iId] = iY3;
                            }
                        }
                    }
                    copyEvent.recycle();
                }
            } catch (Exception e) {
                Log.e(TAG, "Catch multi-touch crash and ignored");
            }
        });
        return true;
    }

    @Override // com.bicore.NativeFuntion.RenderEventListener
    public void SetTimer(int miliseconds) {
        TimerTask tt = new TimerTask() {
            /* class com.bicore.graphic.BitmapSurfaceView.AnonymousClass2 */

            public void run() {
                BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                    /* class com.bicore.graphic.BitmapSurfaceView.AnonymousClass2.AnonymousClass1 */

                    public void run() {
                        BitmapSurfaceView.this.invalidate();
                    }
                });
            }
        };
        if (timer != null) {
            timer.schedule(tt, (long) miliseconds);
        }
    }
}
