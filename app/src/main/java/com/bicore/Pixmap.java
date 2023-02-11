package com.bicore;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/* access modifiers changed from: package-private */
/* compiled from: Font */
public class Pixmap {
    Canvas canvas;
    Paint paint;
    Bitmap pixmap;

    /* compiled from: Font */
    public enum Format {
        Alpha,
        RGB565,
        RGBA4444,
        RGBA8888
    }

    Pixmap(int width, int height, Format format) {
        this.pixmap = Bitmap.createBitmap(width, height, getInternalFormat(format));
        this.canvas = new Canvas(this.pixmap);
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    public Pixmap(Bitmap bitmap) {
        this.pixmap = bitmap;
        if (this.pixmap.isMutable()) {
            this.canvas = new Canvas(this.pixmap);
        }
        this.paint = new Paint();
        this.paint.setStyle(Paint.Style.FILL);
        this.paint.setAntiAlias(true);
        this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
    }

    protected static Bitmap.Config getInternalFormat(Format format) {
        if (format == Format.Alpha) {
            return Bitmap.Config.ALPHA_8;
        }
        if (format == Format.RGBA4444) {
            return Bitmap.Config.ARGB_4444;
        }
        if (format == Format.RGB565) {
            return Bitmap.Config.RGB_565;
        }
        return Bitmap.Config.ARGB_8888;
    }

    public void dispose() {
        this.pixmap.recycle();
        this.pixmap = null;
    }
}
