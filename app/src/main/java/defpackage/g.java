package defpackage;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import com.google.ads.AdActivity;
import com.google.ads.AdSize;

/* renamed from: g  reason: default package */
public final class g extends WebView {
    private AdActivity a;
    private AdSize b;

    public g(Context context, AdSize adSize) {
        super(context);
        this.b = adSize;
        getSettings().setJavaScriptEnabled(true);
        setScrollBarStyle(0);
    }

    public final void a() {
        if (this.a != null) {
            this.a.finish();
        }
    }

    public final void a(AdActivity adActivity) {
        this.a = adActivity;
    }

    public final AdActivity b() {
        return this.a;
    }

    /* access modifiers changed from: protected */
    public final void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (isInEditMode()) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else if (this.b == null) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else {
            int mode = View.MeasureSpec.getMode(widthMeasureSpec);
            int size = View.MeasureSpec.getSize(widthMeasureSpec);
            int mode2 = View.MeasureSpec.getMode(heightMeasureSpec);
            int size2 = View.MeasureSpec.getSize(heightMeasureSpec);
            float f = getContext().getResources().getDisplayMetrics().density;
            int width = (int) (((float) this.b.getWidth()) * f);
            int height = (int) (f * ((float) this.b.getHeight()));
            if (mode == 0 || mode2 == 0) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            } else if (width > size || height > size2) {
                t.e("Not enough space to show ad! Wants: <" + width + ", " + height + ">, Has: <" + size + ", " + size2 + ">");
                setVisibility(8);
                setMeasuredDimension(0, 0);
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        }
    }
}
