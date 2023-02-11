package com.bicore.graphic;

import android.graphics.Bitmap;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

/* access modifiers changed from: package-private */
/* compiled from: BitmapQueueView */
public class BicoreImage {
    static boolean bForceLoad32 = false;
    int BOption;
    int BPalCount;
    boolean bFitImage = false;
    boolean bUseIndexColor = true;
    boolean bUseTransparentType = false;
    boolean bUseTrueColor = false;
    byte[] baAlpha;
    byte[] baPal;
    byte[] baSrc;
    int bciID;
    Bitmap bitmap;
    short sFitHeight;
    short sFitWidth;
    short sFitX;
    short sFitY;
    short sHeight;
    short sWidth;
    ShortBuffer saPalBuffer;
    ShortBuffer saSrc;

    public BicoreImage(int id) {
        this.bciID = id;
    }

    private static int pack8888(int r, int g, int b, int a) {
        return (r << 0) | (g << 8) | (b << 16) | (a << 24);
    }

    private static IntBuffer makeBuffer(int[] src, int n) {
        IntBuffer dst = IntBuffer.allocate(n);
        dst.put(src);
        dst.rewind();
        return dst;
    }

    private static int mul255(int c, int a) {
        int prod = (c * a) + 128;
        return ((prod >> 8) + prod) >> 8;
    }

    private static int mul15(short c, short a) {
        int prod = (c * a) + 8;
        return ((prod >> 4) + prod) >> 4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v248, types: [int] */
    /* JADX WARN: Type inference failed for: r0v260, types: [int] */
    /* JADX WARNING: Unknown variable types count: 2 */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean LoadBCImage(java.io.InputStream r28) {
        /*
        // Method dump skipped, instructions count: 2049
        */
        throw new UnsupportedOperationException("Method not decompiled: com.bicore.graphic.BicoreImage.LoadBCImage(java.io.InputStream):boolean");
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public int getID() {
        return this.bciID;
    }
}
