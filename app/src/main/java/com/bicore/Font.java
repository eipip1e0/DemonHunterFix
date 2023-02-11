package com.bicore;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.Log;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

public class Font {
    private static final ArrayList<Font> buffers = new ArrayList<>();
    private int CurrentTextureId;
    private int CurrentTextureIndex;
    private final int FontTextureHeight = 256;
    private final int FontTextureWidth = 256;
    HashMap<String, Glyph> GlyphMap = new HashMap<>();
    boolean bValidate = false;
    Typeface font;
    private int glyphX = 0;
    private int glyphY = 0;
    ArrayList<Integer> ia = new ArrayList<>();
    Paint.FontMetrics metrics;
    Paint paint;
    Rect tmpRect = new Rect();
    Rect tmpRect2 = new Rect();

    public enum FontStyle {
        Plain,
        Bold,
        Italic,
        BoldItalic
    }

    public native void setFontData(int i, int i2, int i3, int i4);

    public native int setStringData(int i, int i2, int i3, int i4, int i5, byte[] bArr);

    public Font(int size) {
        Log.w("FrameWork", "Font new begin");
        CreateFont(null, size, 0);
        buffers.add(this);
    }

    public Font(String fontName, int size, FontStyle style) {
        CreateFont(fontName, size, getFontStyle(style));
        buffers.add(this);
    }

    private void CreateFont(String fontName, int size, int style) {
        Log.w("FrameWork", "Font CreateFont request size : " + size);
        if (fontName == null) {
            this.font = Typeface.defaultFromStyle(style);
        } else {
            this.font = Typeface.create(fontName, style);
        }
        this.paint = new Paint();
        this.paint.setTypeface(this.font);
        this.paint.setTextSize((float) size);
        this.paint.setAntiAlias(false);
        this.metrics = this.paint.getFontMetrics();
        setFontData(getFontAscent(), getFontDescent(), getLineHeight(), getLineGap());
    }

    private void CreateFontTexture() {
        this.CurrentTextureId = NativeFuntion.nativeCreateEmptyTexture(256, 256, 0, 0, 0);
        this.ia.add(new Integer(this.CurrentTextureId));
        this.glyphX = 0;
        this.glyphY = 0;
        this.bValidate = true;
    }

    private int getFontStyle(FontStyle style) {
        if (style == FontStyle.Bold) {
            return 1;
        }
        if (style == FontStyle.BoldItalic) {
            return 3;
        }
        if (style == FontStyle.Italic) {
            return 2;
        }
        return style == FontStyle.Plain ? 0 : 0;
    }

    public void dispose() {
        Log.w("FrameWork", "Font dispose begin");
        buffers.remove(this);
        invalidateFont();
    }

    public void invalidateFont() {
        for (int i = 0; i < this.ia.size(); i++) {
            int texId = this.ia.get(i).intValue();
            if (texId != 0) {
                NativeFuntion.nativeDeleteTexture(texId);
            }
        }
        this.ia.clear();
        this.GlyphMap.clear();
        this.bValidate = false;
    }

    public static void invalidateAllFontBuffers() {
        for (int i = 0; i < buffers.size(); i++) {
            buffers.get(i).invalidateFont();
        }
    }

    public Glyph getGlyph(String strChar) {
        if (!this.bValidate) {
            CreateFontTexture();
        }
        Glyph g = this.GlyphMap.get(strChar);
        if (g != null) {
            return g;
        }
        Glyph g2 = createGlyph(strChar);
        this.GlyphMap.put(strChar, g2);
        return g2;
    }

    private Glyph createGlyph(String strCharacterText) {
        Pixmap pixmap = getGlyphBitmap(strCharacterText);
        Rect rect = new Rect();
        getGlyphBounds(strCharacterText, rect);
        if (this.glyphX + rect.width() >= 256) {
            this.glyphX = 0;
            this.glyphY += getLineGap() + getLineHeight();
        }
        if (this.glyphY + rect.height() >= 256) {
            CreateFontTexture();
        }
        Bitmap bitmap = pixmap.pixmap;
        ByteBuffer bb = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
        bitmap.copyPixelsToBuffer(bb);
        if (bb.hasArray()) {
            NativeFuntion.nativeTextureSubImage2D(this.CurrentTextureId, bb.array(), this.glyphX, this.glyphY, rect.width(), rect.height());
        }
        bb.clear();
        Glyph glyph = new Glyph(getGlyphAdvance(strCharacterText), rect.width(), rect.height(), ((float) this.glyphX) / 256.0f, ((float) this.glyphY) / 256.0f, ((float) rect.width()) / 256.0f, ((float) rect.height()) / 256.0f);
        glyph.SetTextureId(this.CurrentTextureId);
        glyph.SetFont(this);
        this.glyphX += rect.width();
        return glyph;
    }

    public Pixmap getGlyphBitmap(String strCharacterText) {
        Rect rect = new Rect();
        this.paint.getTextBounds(strCharacterText, 0, 1, rect);
        Bitmap bitmap = Bitmap.createBitmap(rect.width() == 0 ? 1 : rect.width() + 5, getLineHeight(), Bitmap.Config.ALPHA_8);
        Canvas g = new Canvas(bitmap);
        this.paint.setAntiAlias(true);
        this.paint.setColor(0);
        this.paint.setStyle(Paint.Style.FILL);
        g.drawRect(new Rect(0, 0, rect.width() + 5, getLineHeight()), this.paint);
        this.paint.setColor(-1);
        g.drawText(strCharacterText, 0.0f, -this.metrics.ascent, this.paint);
        return new Pixmap(bitmap);
    }

    public int getLineGap() {
        return (int) Math.ceil((double) this.metrics.leading);
    }

    public int getLineHeight() {
        return (int) Math.ceil((double) (Math.abs(this.metrics.ascent) + Math.abs(this.metrics.descent)));
    }

    public int getFontAscent() {
        return (int) Math.ceil((double) Math.abs(this.metrics.ascent));
    }

    public int getFontDescent() {
        return (int) Math.ceil((double) Math.abs(this.metrics.descent));
    }

    public int getStringWidth(String text) {
        return (int) this.paint.measureText(text);
    }

    public void getGlyphBounds(String strCharacterText, Rect rect) {
        this.paint.getTextBounds(strCharacterText, 0, 1, this.tmpRect);
        rect.right = rect.right + (this.tmpRect.width() - rect.width()) + 5;
        rect.bottom += getLineHeight() - rect.height();
    }

    public int getGlyphAdvance(String strCharacterText) {
        float[] width = new float[10];
        this.paint.getTextWidths(strCharacterText, width);
        return (int) Math.ceil((double) width[0]);
    }

    public int getStringData(String str) {
        Rect rect = new Rect();
        this.paint.getTextBounds(str, 0, 1, rect);
        Bitmap bitmap = getGlyphBitmap(str).pixmap;
        int stringHeight = rect.height();
        float[] widths = new float[10];
        this.paint.getTextWidths(str, widths);
        ByteBuffer bb = ByteBuffer.allocate(bitmap.getRowBytes() * bitmap.getHeight());
        bitmap.copyPixelsToBuffer(bb);
        return setStringData((int) this.paint.measureText(str), stringHeight, (int) Math.ceil((double) widths[0]), bitmap.getWidth(), bitmap.getHeight(), bb.array());
    }
}
