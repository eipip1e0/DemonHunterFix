package com.bicore.graphic;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.Xfermode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.bicore.BicoreActivity;
import com.bicore.IntHashMap;
import com.bicore.NativeFuntion;
import com.bicore.dhunter.R;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class BitmapQueueView extends View implements NativeFuntion.RenderEventListener, NativeFuntion.BitmapCraterEventListener {
    private static final Xfermode[] sModes = {new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER), new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN), new PorterDuffXfermode(PorterDuff.Mode.DARKEN), new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY), new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.SCREEN), new PorterDuffXfermode(PorterDuff.Mode.CLEAR), new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP), new PorterDuffXfermode(PorterDuff.Mode.SRC_IN), new PorterDuffXfermode(PorterDuff.Mode.CLEAR)};
    static Timer timer = new Timer(true);
    final int IDXG_ITT_180 = 7;
    final int IDXG_ITT_90CCW = 4;
    final int IDXG_ITT_90CW = 3;
    final int IDXG_ITT_HF90CCW = 6;
    final int IDXG_ITT_HF90CW = 5;
    final int IDXG_ITT_HORIZ = 1;
    final int IDXG_ITT_VERT = 2;
    final int OPERATOR_CLEAR = 0;
    final int OPERATOR_CLIP = 1;
    final int OPERATOR_COLOR = 6;
    final int OPERATOR_END = 100;
    final int OPERATOR_FONT = 7;
    final int OPERATOR_IMAGE = 4;
    final int OPERATOR_LINE = 2;
    final int OPERATOR_RECT = 3;
    final int OPERATOR_TEXT = 5;
    Paint blush = new Paint();
    Paint dstBlush = new Paint(2);
    Queue<MotionEvent> eventQueue = new LinkedList();
    ColorFilter filter = new ColorFilter();
    IntHashMap<BicoreImage> ht = new IntHashMap<>(100);
    int[] lx = new int[8];
    int[] ly = new int[8];
    private Canvas originalCanvas = new Canvas(this.surfaceBitmap);
    private Bitmap surfaceBitmap = Bitmap.createBitmap(BicoreActivity.iContentsWidth, BicoreActivity.iContentsHeight, Bitmap.Config.ARGB_4444);

    public native byte[] GetRenderQueue();

    public BitmapQueueView(Context context) {
        super(context);
        NativeFuntion.setRenderEventListener(this);
        NativeFuntion.setBitmapCreaterEventListener(this);
    }

    @Override // com.bicore.NativeFuntion.RenderEventListener
    public void SetTimer(int miliseconds) {
        TimerTask tt = new TimerTask() {
            /* class com.bicore.graphic.BitmapQueueView.AnonymousClass1 */

            public void run() {
                BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                    /* class com.bicore.graphic.BitmapQueueView.AnonymousClass1.AnonymousClass1 */

                    public void run() {
                        BitmapQueueView.this.invalidate();
                    }
                });
            }
        };
        if (timer != null) {
            timer.schedule(tt, (long) miliseconds);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        Log.w("BicoreActivity", "*GLSurfaceView - onTouchEvent orginal : " + event);
        this.eventQueue.add(MotionEvent.obtain(event));
        BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
            /* class com.bicore.graphic.BitmapQueueView.AnonymousClass2 */

            public void run() {
                MotionEvent copyEvent = BitmapQueueView.this.eventQueue.poll();
                if (copyEvent != null) {
                    int actionEvent = copyEvent.getAction();
                    int actionType = actionEvent & 255;
                    if (actionType == 0) {
                        int iX = (((int) copyEvent.getX(0)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth;
                        int iY = (((int) copyEvent.getY(0)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight;
                        int actionPointerID = copyEvent.getPointerId(0);
                        NativeFuntion.nativeHandleEvent(23, iX, iY, iX, iY);
                        BitmapQueueView.this.lx[actionPointerID] = iX;
                        BitmapQueueView.this.ly[actionPointerID] = iY;
                    } else if (actionType == 1) {
                        int actionPointerID2 = copyEvent.getPointerId(0);
                        NativeFuntion.nativeHandleEvent(24, (((int) copyEvent.getX(0)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth, (((int) copyEvent.getY(0)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight, BitmapQueueView.this.lx[actionPointerID2], BitmapQueueView.this.ly[actionPointerID2]);
                        BitmapQueueView.this.lx[actionPointerID2] = 0;
                        BitmapQueueView.this.ly[actionPointerID2] = 0;
                    } else if (actionType == 5) {
                        int actionPointerID3 = (actionEvent & 65280) >> 8;
                        int actionPointerIndex = copyEvent.findPointerIndex(actionPointerID3);
                        int iX2 = (((int) copyEvent.getX(actionPointerIndex)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth;
                        int iY2 = (((int) copyEvent.getY(actionPointerIndex)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight;
                        NativeFuntion.nativeHandleEvent(23, iX2, iY2, iX2, iY2);
                        BitmapQueueView.this.lx[actionPointerID3] = iX2;
                        BitmapQueueView.this.ly[actionPointerID3] = iY2;
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
                        NativeFuntion.nativeHandleEvent(24, (((int) copyEvent.getX(actionPointerIndex2)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth, (((int) copyEvent.getY(actionPointerIndex2)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight, BitmapQueueView.this.lx[actionPointerID4], BitmapQueueView.this.ly[actionPointerID4]);
                        BitmapQueueView.this.lx[actionPointerID4] = 0;
                        BitmapQueueView.this.ly[actionPointerID4] = 0;
                    } else if (actionType == 2) {
                        int actionPointerCount2 = copyEvent.getPointerCount();
                        for (int i2 = 0; i2 < actionPointerCount2; i2++) {
                            int iId = copyEvent.getPointerId(i2);
                            int iX3 = (((int) copyEvent.getX(iId)) * BicoreActivity.iContentsWidth) / BicoreActivity.iScreenWidth;
                            int iY3 = (((int) copyEvent.getY(iId)) * BicoreActivity.iContentsHeight) / BicoreActivity.iScreenHeight;
                            if (!(iX3 == BitmapQueueView.this.lx[iId] || iY3 == BitmapQueueView.this.ly[iId])) {
                                NativeFuntion.nativeHandleEvent(26, iX3, iY3, BitmapQueueView.this.lx[iId], BitmapQueueView.this.ly[iId]);
                                BitmapQueueView.this.lx[iId] = iX3;
                                BitmapQueueView.this.ly[iId] = iY3;
                            }
                        }
                    }
                    copyEvent.recycle();
                }
            }
        });
        return true;
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        this.originalCanvas.clipRect(0, 0, BicoreActivity.iContentsWidth, BicoreActivity.iContentsHeight);
        try {
            DrawByStream(new ByteArrayInputStream(GetRenderQueue()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        canvas.drawBitmap(this.surfaceBitmap, (Rect) null, new Rect(0, 0, BicoreActivity.iScreenWidth, BicoreActivity.iScreenHeight), this.dstBlush);
    }

    private void DrawByStream(InputStream in) throws Exception {
        Bitmap bitmap;
        DataInputStream dis = new DataInputStream(in);
        boolean saved = false;
        int clipcount = 0;
        int operatorCount = 0;
        Log.e("test", "operatorStart");
        while (dis.available() > 0) {
            operatorCount++;
            switch (dis.readUnsignedByte()) {
                case 0:
                    this.originalCanvas.drawARGB(255, 0, 0, 0);
                    break;
                case 1:
                    short l = Short.reverseBytes(dis.readShort());
                    short t = Short.reverseBytes(dis.readShort());
                    short r = Short.reverseBytes(dis.readShort());
                    short b = Short.reverseBytes(dis.readShort());
                    if (saved) {
                        this.originalCanvas.restore();
                    }
                    this.originalCanvas.save();
                    this.originalCanvas.clipRect(new Rect(l, t, r + 1, b + 1));
                    clipcount++;
                    saved = true;
                    break;
                case 2:
                    short x1 = Short.reverseBytes(dis.readShort());
                    short y1 = Short.reverseBytes(dis.readShort());
                    short x2 = Short.reverseBytes(dis.readShort());
                    short y2 = Short.reverseBytes(dis.readShort());
                    int color = Integer.reverseBytes(dis.readInt());
                    this.blush.reset();
                    this.blush.setColor(color);
                    this.originalCanvas.drawLine((float) x1, (float) y1, (float) x2, (float) y2, this.blush);
                    break;
                case 3:
                    short left = Short.reverseBytes(dis.readShort());
                    short top = Short.reverseBytes(dis.readShort());
                    short right = Short.reverseBytes(dis.readShort());
                    short bottom = Short.reverseBytes(dis.readShort());
                    int color2 = Integer.reverseBytes(dis.readInt());
                    this.blush.reset();
                    this.blush.setColor(color2);
                    this.originalCanvas.drawRect((float) left, (float) top, (float) right, (float) bottom, this.blush);
                    break;
                case 4:
                    short dstx = Short.reverseBytes(dis.readShort());
                    short dsty = Short.reverseBytes(dis.readShort());
                    short dstwidth = Short.reverseBytes(dis.readShort());
                    short dstheight = Short.reverseBytes(dis.readShort());
                    int bitId = Short.reverseBytes(dis.readShort());
                    dis.readUnsignedByte();
                    int transformType = dis.readUnsignedByte();
                    int BAlpha = dis.readUnsignedByte();
                    int overlaycolor = Integer.reverseBytes(dis.readInt());
                    BicoreImage bci = this.ht.get(bitId);
                    if (!(bci == null || (bitmap = bci.getBitmap()) == null)) {
                        if (overlaycolor != 0) {
                            this.blush.setFilterBitmap(true);
                            this.blush.setColorFilter(new PorterDuffColorFilter(overlaycolor, PorterDuff.Mode.SRC_IN));
                            boolean z = bci.bUseTransparentType;
                        } else {
                            this.blush.setAlpha(BAlpha);
                            this.blush.setFilterBitmap(false);
                            this.blush.setColorFilter(null);
                            boolean z2 = bci.bUseTransparentType;
                        }
                        if (transformType != 0) {
                            this.originalCanvas.save();
                        }
                        switch (transformType) {
                            case 1:
                                this.originalCanvas.scale(-1.0f, 1.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                            case 2:
                                this.originalCanvas.scale(1.0f, -1.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                            case 3:
                                this.originalCanvas.rotate(90.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                            case 4:
                                this.originalCanvas.rotate(-90.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                            case 5:
                                this.originalCanvas.scale(-1.0f, 1.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                this.originalCanvas.rotate(90.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                            case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                                this.originalCanvas.scale(-1.0f, 1.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                this.originalCanvas.rotate(-90.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                            case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
                                this.originalCanvas.rotate(180.0f, (float) ((dstwidth / 2) + dstx), (float) ((dstheight / 2) + dsty));
                                break;
                        }
                        this.originalCanvas.drawBitmap(bitmap, (Rect) null, new Rect(dstx, dsty, dstx + dstwidth, dsty + dstheight), this.blush);
                        if (transformType == 0) {
                            break;
                        } else {
                            this.originalCanvas.restore();
                            break;
                        }
                    }
                case 5:
                    short x = Short.reverseBytes(dis.readShort());
                    short y = Short.reverseBytes(dis.readShort());
                    int reverseBytes = Short.reverseBytes(dis.readShort());
                    byte[] buffer = new byte[reverseBytes];
                    dis.read(buffer, 0, reverseBytes);
                    String text = new String(buffer);
                    int color3 = Integer.reverseBytes(dis.readInt());
                    this.blush.reset();
                    this.blush.setTypeface(Typeface.defaultFromStyle(0));
                    this.blush.setColor(color3);
                    this.originalCanvas.drawText(text, (float) x, (float) y, this.blush);
                    break;
                case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                    this.blush.setARGB(dis.readUnsignedByte(), dis.readUnsignedByte(), dis.readUnsignedByte(), dis.readUnsignedByte());
                    break;
                case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
                    dis.readUnsignedByte();
                    int bold = dis.readUnsignedByte();
                    int size = dis.readUnsignedByte();
                    if (bold == 0) {
                        this.blush.setTypeface(Typeface.DEFAULT);
                    } else {
                        this.blush.setTypeface(Typeface.DEFAULT_BOLD);
                    }
                    this.blush.setTextSize((float) size);
                    break;
                case 100:
                    if (saved) {
                        this.originalCanvas.restore();
                    }
                    Log.e("test", "operatorCount = " + operatorCount);
                    return;
            }
        }
        if (saved) {
            this.originalCanvas.restore();
        }
    }

    @Override // com.bicore.NativeFuntion.BitmapCraterEventListener
    public int CreateBitmap(int id, String filename, int offset, int length) {
        BicoreBitmap bb = new BicoreBitmap();
        bb.filename = filename;
        bb.offset = offset;
        bb.length = length;
        BicoreImage bi = new BicoreImage(id);
        try {
            Log.i("CreateBitmap", "ResID:" + id + " ,File:" + filename + " ,Offset:" + offset);
            InputStream is = getContext().getResources().getAssets().open("I/" + filename);
            if (is.available() > 0) {
                is.skip((long) offset);
            }
            if (is.available() <= 0 || !bi.LoadBCImage(is)) {
                is.close();
                return 0;
            }
            is.close();
            Log.w("CreateBitmap", "*LoadBCImage Succeed, " + bi.getID());
            this.ht.put(id, bi);
            return bi.getID();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override // com.bicore.NativeFuntion.BitmapCraterEventListener
    public int CreateBitmap(int width, int heihgt, byte[] bitdata) {
        return 0;
    }

    @Override // com.bicore.NativeFuntion.BitmapCraterEventListener
    public void DestoryBitmap(int id) {
        Log.i("DestoryBitmap", "ResID:" + id);
        if (this.ht.containsKey((long) id)) {
            this.ht.remove(id);
        }
    }
}
