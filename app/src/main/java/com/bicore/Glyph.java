package com.bicore;

import android.graphics.Rect;
import android.util.Log;

/* access modifiers changed from: package-private */
/* compiled from: Font */
public class Glyph {
    public int advance;
    public Font font;
    public int height;
    Rect rect = new Rect();
    public int texId;
    public float u;
    public float uWidth;
    public float v;
    public float vHeight;
    public int width;

    public Glyph(int advance2, int width2, int height2, float u2, float v2, float uWidth2, float vHeight2) {
        this.advance = advance2;
        this.width = width2;
        this.height = height2;
        this.u = u2;
        this.v = v2;
        this.uWidth = uWidth2;
        this.vHeight = vHeight2;
        Log.w("FrameWork", "New Glyph " + width2 + "," + height2 + "," + u2 + "," + v2 + "," + uWidth2 + "," + vHeight2);
    }

    /* access modifiers changed from: package-private */
    public int GetTextureId() {
        return this.texId;
    }

    /* access modifiers changed from: package-private */
    public void SetTextureId(int id) {
        this.texId = id;
    }

    /* access modifiers changed from: package-private */
    public void SetFont(Font font2) {
        this.font = font2;
    }

    public void DrawSelf(int x, int y, int color, int alpha, int l, int t, int r, int b) {
        int y2 = y - this.font.getFontAscent();
        int w = this.width;
        int h = this.height;
        float fu = this.u;
        float fv = this.v;
        float fuw = this.uWidth;
        float fvh = this.vHeight;
        if (x + w > r) {
            fuw -= ((float) ((x + w) - r)) / 256.0f;
            w = r - x;
            if (w < 0) {
                return;
            }
        }
        if (x < l) {
            w -= l - x;
            float temp = ((float) (l - x)) / 256.0f;
            fu += temp;
            fuw -= temp;
            x = l;
        }
        if (y2 + h > b) {
            fvh -= ((float) ((y2 + h) - b)) / 256.0f;
            h = b - y2;
            if (h < 0) {
                return;
            }
        }
        if (y2 < t) {
            h -= t - y2;
            float temp2 = ((float) (t - y2)) / 256.0f;
            fv += temp2;
            fvh -= temp2;
            y2 = t;
        }
        if (w > 0 && h > 0 && fuw > 0.0f && fvh > 0.0f) {
            NativeFuntion.nativeDrawString(this.texId, x, y2, w, h, fu, fv, fuw, fvh, color, alpha);
        }
    }
}
