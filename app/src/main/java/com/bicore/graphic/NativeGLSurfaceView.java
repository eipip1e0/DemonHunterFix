package com.bicore.graphic;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;
import com.bicore.BicoreActivity;
import com.bicore.Font;
import com.bicore.NativeFuntion;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public class NativeGLSurfaceView extends GLSurfaceView implements NativeFuntion.RenderEventListener, GLSurfaceView.EGLWindowSurfaceFactory, GLSurfaceView.Renderer {
    static Timer timer = new Timer(true);
    Queue<MotionEvent> eventQueue = new LinkedList();
    int[] lx = new int[8];
    int[] ly = new int[8];

    public NativeGLSurfaceView(Context context) {
        super(context);
        Log.w("BicoreActivity", "*GLSurfaceView - DemoGLSurfaceView");
        setRenderer(this);
        setRenderMode(0);
        for (int i = 0; i < 8; i++) {
            this.lx[i] = 0;
            this.ly[i] = 0;
        }
        SetTimer(100);
        NativeFuntion.setRenderEventListener(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        this.eventQueue.add(MotionEvent.obtain(event));
        queueEvent(new Runnable() {
            /* class com.bicore.graphic.NativeGLSurfaceView.AnonymousClass1 */

            public void run() {
                MotionEvent copyEvent = NativeGLSurfaceView.this.eventQueue.poll();
                if (copyEvent != null) {
                    int actionEvent = copyEvent.getAction();
                    int actionType = actionEvent & 255;
                    if (actionType == 0) {
                        int iX = (int) copyEvent.getX(0);
                        int iY = (int) copyEvent.getY(0);
                        int actionPointerID = copyEvent.getPointerId(0);
                        NativeFuntion.nativeHandleEvent(23, iX, iY, iX, iY);
                        NativeGLSurfaceView.this.lx[actionPointerID] = iX;
                        NativeGLSurfaceView.this.ly[actionPointerID] = iY;
                    } else if (actionType == 1) {
                        int actionPointerID2 = copyEvent.getPointerId(0);
                        NativeFuntion.nativeHandleEvent(24, (int) copyEvent.getX(0), (int) copyEvent.getY(0), NativeGLSurfaceView.this.lx[actionPointerID2], NativeGLSurfaceView.this.ly[actionPointerID2]);
                        NativeGLSurfaceView.this.lx[actionPointerID2] = 0;
                        NativeGLSurfaceView.this.ly[actionPointerID2] = 0;
                    } else if (actionType == 5) {
                        int actionPointerID3 = (actionEvent & 65280) >> 8;
                        int actionPointerIndex = copyEvent.findPointerIndex(actionPointerID3);
                        int iX2 = (int) copyEvent.getX(actionPointerIndex);
                        int iY2 = (int) copyEvent.getY(actionPointerIndex);
                        NativeFuntion.nativeHandleEvent(23, iX2, iY2, iX2, iY2);
                        NativeGLSurfaceView.this.lx[actionPointerID3] = iX2;
                        NativeGLSurfaceView.this.ly[actionPointerID3] = iY2;
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
                        NativeFuntion.nativeHandleEvent(24, (int) copyEvent.getX(actionPointerIndex2), (int) copyEvent.getY(actionPointerIndex2), NativeGLSurfaceView.this.lx[actionPointerID4], NativeGLSurfaceView.this.ly[actionPointerID4]);
                        NativeGLSurfaceView.this.lx[actionPointerID4] = 0;
                        NativeGLSurfaceView.this.ly[actionPointerID4] = 0;
                    } else if (actionType == 2) {
                        int actionPointerCount2 = copyEvent.getPointerCount();
                        for (int i2 = 0; i2 < actionPointerCount2; i2++) {
                            int iId = copyEvent.getPointerId(i2);
                            int iX3 = (int) copyEvent.getX(iId);
                            int iY3 = (int) copyEvent.getY(iId);
                            if (!(iX3 == NativeGLSurfaceView.this.lx[iId] || iY3 == NativeGLSurfaceView.this.ly[iId])) {
                                NativeFuntion.nativeHandleEvent(26, iX3, iY3, NativeGLSurfaceView.this.lx[iId], NativeGLSurfaceView.this.ly[iId]);
                                NativeGLSurfaceView.this.lx[iId] = iX3;
                                NativeGLSurfaceView.this.ly[iId] = iY3;
                            }
                        }
                    }
                    copyEvent.recycle();
                }
            }
        });
        return true;
    }

    @Override // com.bicore.NativeFuntion.RenderEventListener
    public void SetTimer(int miliseconds) {
        TimerTask tt = new TimerTask() {
            /* class com.bicore.graphic.NativeGLSurfaceView.AnonymousClass2 */

            public void run() {
                NativeGLSurfaceView.this.requestRender();
            }
        };
        if (timer != null) {
            timer.schedule(tt, (long) miliseconds);
        }
    }

    public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
        return egl.eglCreateWindowSurface(display, config, nativeWindow, null);
    }

    public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
        egl.eglDestroySurface(display, surface);
        Log.i("BicoreActivity", "*eglTerminate ");
    }

    public void onDrawFrame(GL10 gl) {
        if (!BicoreActivity.GetActivity().isFinishing()) {
            NativeFuntion.nativeRender();
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.w("BicoreActivity", "*Renderer - onSurfaceChanged");
        NativeFuntion.nativeResize(width, height);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.w("BicoreActivity", "*Renderer - onSurfaceCreated");
        Font.invalidateAllFontBuffers();
        NativeFuntion.nativeInvaildateAllTexture();
        NativeFuntion.nativeInit();
    }
}
