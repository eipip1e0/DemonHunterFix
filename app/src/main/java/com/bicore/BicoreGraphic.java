package com.bicore;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bicore.NativeFuntion;
import com.bicore.graphic.BitmapQueueView;
import com.bicore.graphic.BitmapSurfaceView;
import com.bicore.graphic.NativeGLSurfaceView;
import com.feelingk.iap.util.Defines;
import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import java.util.Timer;
import java.util.TimerTask;

public class BicoreGraphic extends RelativeLayout implements NativeFuntion.FrameEventListener {
    static Timer timer = new Timer(true);
    private final int EditBoxDeleteText = Defines.DIALOG_STATE.DLG_PURCHASE_PROGRESS;
    private final int EditBoxHide = Defines.DIALOG_STATE.DLG_ERROR;
    int EditBoxInputType;
    private final int EditBoxInsertText = Defines.DIALOG_STATE.DLG_PURCHASE;
    private final int EditBoxSetPosition = Defines.DIALOG_STATE.DLG_LOADING_PROGRESS;
    private final int EditBoxShow = 100;
    String bannerURL;
    final Activity context;
    int iTimeLimit;
    int length;
    int lx;
    int ly;
    View.OnClickListener mAdClickListener = new View.OnClickListener() {
        /* class com.bicore.BicoreGraphic.AnonymousClass2 */

        public void onClick(View v) {
            Log.w("BicoreActivity", "*Ad OnClicked");
            if (BicoreGraphic.this.mAdViewState != 0 && BicoreGraphic.this.mAdViewShow != 0) {
                BicoreGraphic.this.mAdClickTime = System.currentTimeMillis();
                BicoreGraphic.this.mAdClicked = true;
            }
        }
    };
    long mAdClickTime;
    boolean mAdClicked = false;
    AdListener mAdListener = new AdListener() {
        /* class com.bicore.BicoreGraphic.AnonymousClass3 */

        @Override // com.google.ads.AdListener
        public void onDismissScreen(Ad ad) {
        }

        @Override // com.google.ads.AdListener
        public void onFailedToReceiveAd(Ad ad, AdRequest.ErrorCode er) {
            Log.w("BicoreActivity", "failed to receive ad");
            BicoreGraphic.this.mAdViewState = 0;
        }

        @Override // com.google.ads.AdListener
        public void onLeaveApplication(Ad ad) {
        }

        @Override // com.google.ads.AdListener
        public void onPresentScreen(Ad ad) {
            if (BicoreGraphic.this.mAdViewState != 0 && BicoreGraphic.this.mAdViewShow != 0) {
                BicoreGraphic.this.mAdClickTime = System.currentTimeMillis();
                BicoreGraphic.this.mAdClicked = true;
            }
        }

        @Override // com.google.ads.AdListener
        public void onReceiveAd(Ad ad) {
            BicoreGraphic.this.mAdViewState = 1;
            Log.w("BicoreActivityp", "receive ad");
        }
    };
    AdView mAdView;
    int mAdViewShow;
    int mAdViewState;
    RelativeLayout.LayoutParams mAdmobParam;
    RelativeLayout.LayoutParams mBannerParam;
    WebView mBannerView;
    EditText mEdit;
    RelativeLayout.LayoutParams mEditparam;
    View mGraphicView;
    Handler mHandler = new Handler() {
        /* class com.bicore.BicoreGraphic.AnonymousClass1 */

        public void handleMessage(Message msg) {
            Log.w("BicoreActivity", "Recived handleMessage" + msg.toString());
            if (msg.what == 100) {
                BicoreGraphic.this.mEdit.setLayoutParams(BicoreGraphic.this.mEditparam);
                BicoreGraphic.this.mEdit.getText().append((CharSequence) BicoreGraphic.this.strInsertText);
                BicoreGraphic.this.strInsertText = "";
                BicoreGraphic.this.mEdit.setVisibility(0);
                BicoreGraphic.this.mEdit.setPressed(true);
                BicoreGraphic.this.mEdit.setInputType(BicoreGraphic.this.EditBoxInputType);
                BicoreGraphic.this.mEdit.onTouchEvent(MotionEvent.obtain(msg.getWhen(), msg.getWhen(), 1, ((float) BicoreGraphic.this.mEditparam.leftMargin) + 10.0f, ((float) BicoreGraphic.this.mEditparam.topMargin) + 3.0f, 0));
            } else if (msg.what == 101) {
                BicoreGraphic.this.mEdit.setVisibility(4);
                BicoreGraphic.this.mEdit.clearFocus();
                BicoreGraphic.this.mEdit.getText().clear();
            } else if (msg.what == 102) {
                BicoreGraphic.this.mEdit.setLayoutParams(BicoreGraphic.this.mEditparam);
            } else if (msg.what == 103) {
                BicoreGraphic.this.mEdit.getText().append((CharSequence) BicoreGraphic.this.strInsertText);
                BicoreGraphic.this.strInsertText = "";
                Log.w("BicoreActivity", "*InsertMessage : string = " + BicoreGraphic.this.strInsertText + " length :" + BicoreGraphic.this.length);
            } else if (msg.what == 104) {
                BicoreGraphic.this.mEdit.getText().clear();
                Log.w("BicoreActivity", "*DeleteMessage : start :" + BicoreGraphic.this.start + " length :" + BicoreGraphic.this.length);
            }
        }
    };
    TextView mText;
    long mWebAdClickTime;
    boolean mWebAdClicked = false;
    int start;
    String strEditBoxString;
    String strInsertText;

    public native int GetDrawingType();

    public BicoreGraphic(Context context2) {
        super(context2);
        this.context = (Activity) context2;
        switch (GetDrawingType()) {
            case 0:
                this.mGraphicView = new NativeGLSurfaceView(getContext());
                break;
            case 1:
                this.mGraphicView = new BitmapSurfaceView(getContext());
                break;
            case 2:
                this.mGraphicView = new BitmapQueueView(getContext());
                break;
        }
        addView(this.mGraphicView, new RelativeLayout.LayoutParams(-1, -1));
        this.mEdit = new EditText(getContext());
        this.mEdit.setTextSize(12.0f);
        this.mEdit.setSingleLine();
        this.mEdit.setFadingEdgeLength(0);
        this.mEdit.setVerticalFadingEdgeEnabled(false);
        this.mEdit.setHorizontalFadingEdgeEnabled(false);
        this.mEdit.setCompoundDrawablePadding(0);
        this.mEdit.setIncludeFontPadding(false);
        this.mEdit.setVisibility(4);
        this.strInsertText = "";
        this.mEditparam = new RelativeLayout.LayoutParams(100, 100);
        this.mEditparam.leftMargin = KTMarket.USER_ACCREDIT_ERROR;
        this.mEditparam.topMargin = 50;
        addView(this.mEdit, this.mEditparam);
        this.EditBoxInputType = 1;
        NativeFuntion.setFrameEventListener(this);
        this.mBannerView = new WebView(getContext());
        WebSettings ws = this.mBannerView.getSettings();
        ws.setCacheMode(-1);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);
        ws.setJavaScriptEnabled(true);
        ws.setSupportZoom(false);
        this.mBannerView.setWebViewClient(new InnerView());
        this.mBannerView.setVisibility(4);
        this.mBannerView.setInitialScale(100);
        this.mBannerView.setScrollbarFadingEnabled(false);
        this.mBannerView.setVerticalScrollBarEnabled(false);
        this.mBannerView.setHorizontalScrollBarEnabled(false);
        this.mBannerParam = new RelativeLayout.LayoutParams(100, 100);
        this.mBannerParam.leftMargin = 0;
        this.mBannerParam.topMargin = 0;
        this.mBannerParam.width = 0;
        this.mBannerParam.height = 0;
        addView(this.mBannerView, this.mBannerParam);
        this.lx = 0;
        this.ly = 0;
        this.mAdView = new AdView(BicoreActivity.GetActivity(), AdSize.BANNER, BicoreActivity.AdmobID);
        this.mAdView.loadAd(new AdRequest());
        this.mAdView.setVisibility(4);
        this.mAdmobParam = new RelativeLayout.LayoutParams(-2, -2);
        this.mAdmobParam.leftMargin = 0;
        this.mAdmobParam.topMargin = 0;
        addView(this.mAdView, this.mAdmobParam);
        this.mAdView.setAdListener(this.mAdListener);
        this.iTimeLimit = 0;
    }

    /* access modifiers changed from: package-private */
    public class InnerView extends WebViewClient {
        InnerView() {
        }

        @Override // android.webkit.WebViewClient
        public boolean shouldOverrideUrlLoading(WebView view, final String url) {
            Log.w("InnerView", "shouldOverrideUrlLoading URL = " + url);
            if (url.matches(BicoreGraphic.this.bannerURL)) {
                view.loadUrl(url);
                return true;
            }
            BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                /* class com.bicore.BicoreGraphic.InnerView.AnonymousClass1 */

                public void run() {
                    Timer timer = BicoreGraphic.timer;
                    final String str = url;
                    timer.schedule(new TimerTask() {
                        /* class com.bicore.BicoreGraphic.InnerView.AnonymousClass1.AnonymousClass1 */

                        public void run() {
                            BicoreGraphic.this.mWebAdClickTime = System.currentTimeMillis();
                            BicoreGraphic.this.mWebAdClicked = true;
                            BicoreGraphic.this.context.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                        }
                    }, 1000);
                }
            });
            return true;
        }
    }

    class OuterView extends WebChromeClient {
        OuterView() {
        }

        public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
            Log.w("OuterView", "onCreateWindow : " + dialog + " : " + userGesture + " : " + BicoreGraphic.this.mBannerView + " =? " + view);
            return false;
        }
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public EditText GetEditBoxControl() {
        return this.mEdit;
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void EnableControl(EditText v, boolean bEnable) {
        Log.w("BicoreActivity", "*EnableControl : " + v.toString() + " enable = " + new Boolean(bEnable).toString());
        if (bEnable) {
            this.mHandler.sendEmptyMessage(100);
        } else {
            this.mHandler.sendEmptyMessage(Defines.DIALOG_STATE.DLG_ERROR);
        }
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void ShowBanner(int l, int t, int w, int h, String url) {
        this.mBannerParam.leftMargin = (BicoreActivity.iScreenWidth * l) / BicoreActivity.iContentsWidth;
        this.mBannerParam.topMargin = (BicoreActivity.iScreenHeight * t) / BicoreActivity.iContentsHeight;
        this.mBannerParam.width = (BicoreActivity.iScreenWidth * w) / BicoreActivity.iContentsWidth;
        this.mBannerParam.height = (BicoreActivity.iScreenHeight * h) / BicoreActivity.iContentsHeight;
        this.bannerURL = url;
        this.context.runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreGraphic.AnonymousClass4 */

            public void run() {
                BicoreGraphic.this.mBannerView.setLayoutParams(BicoreGraphic.this.mBannerParam);
                BicoreGraphic.this.mBannerView.loadUrl(BicoreGraphic.this.bannerURL);
                BicoreGraphic.this.mBannerView.setVisibility(0);
            }
        });
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void HideBanner() {
        this.context.runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreGraphic.AnonymousClass5 */

            public void run() {
                BicoreGraphic.this.mBannerView.setVisibility(4);
            }
        });
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void ShowAdmob(int l, int t, int time) {
        this.mAdmobParam.leftMargin = (BicoreActivity.iScreenWidth * l) / BicoreActivity.iContentsWidth;
        this.mAdmobParam.topMargin = (BicoreActivity.iScreenHeight * t) / BicoreActivity.iContentsHeight;
        this.iTimeLimit = time;
        if (this.mAdViewState == 1) {
            Log.w("BicoreActivity", "*Ad Show");
            this.mAdViewShow = 1;
            this.context.runOnUiThread(new Runnable() {
                /* class com.bicore.BicoreGraphic.AnonymousClass6 */

                public void run() {
                    BicoreGraphic.this.mAdView.setLayoutParams(BicoreGraphic.this.mAdmobParam);
                    BicoreGraphic.this.mAdView.setVisibility(0);
                    BicoreGraphic.this.mAdView.setClickable(true);
                }
            });
            return;
        }
        Log.w("BicoreActivity", "*Ad not Show");
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void HideAdmob() {
        Log.w("BicoreActivity", "*Ad Hide");
        this.context.runOnUiThread(new Runnable() {
            /* class com.bicore.BicoreGraphic.AnonymousClass7 */

            public void run() {
                BicoreGraphic.this.mAdView.setVisibility(4);
                BicoreGraphic.this.mAdView.setClickable(false);
            }
        });
        this.mAdViewShow = 0;
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public int GetAdState() {
        return this.mAdViewState;
    }

    public void onResume() {
        if (this.mAdClicked) {
            if (System.currentTimeMillis() - this.mAdClickTime > ((long) this.iTimeLimit)) {
                Log.d("BicoreGraphic", "Adshowen more " + this.iTimeLimit + "ms");
                BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                    /* class com.bicore.BicoreGraphic.AnonymousClass8 */

                    public void run() {
                        int iX = BicoreGraphic.this.mAdmobParam.leftMargin + (BicoreGraphic.this.mAdView.getWidth() / 2);
                        int iY = BicoreGraphic.this.mAdmobParam.topMargin + (BicoreGraphic.this.mAdView.getHeight() / 2);
                        NativeFuntion.nativeHandleEvent(23, iX, iY, iX, iY);
                        NativeFuntion.nativeHandleEvent(24, iX, iY, iX, iY);
                    }
                });
            } else {
                Log.d("BicoreGraphic", "Adshowen less " + this.iTimeLimit + "ms");
            }
            this.mAdClicked = false;
        } else if (this.mWebAdClicked) {
            if (System.currentTimeMillis() - this.mWebAdClickTime > ((long) this.iTimeLimit)) {
                Log.d("BicoreGraphic", "WebAdshowen more " + this.iTimeLimit + "ms");
                BicoreActivity.GetActivity().runOnUiThread(new Runnable() {
                    /* class com.bicore.BicoreGraphic.AnonymousClass9 */

                    public void run() {
                        int iX = BicoreGraphic.this.mBannerParam.leftMargin + (BicoreGraphic.this.mBannerParam.width / 2);
                        int iY = BicoreGraphic.this.mBannerParam.topMargin + (BicoreGraphic.this.mBannerParam.height / 2);
                        NativeFuntion.nativeHandleEvent(23, iX, iY, iX, iY);
                        NativeFuntion.nativeHandleEvent(24, iX, iY, iX, iY);
                    }
                });
            } else {
                Log.d("BicoreGraphic", "WebAdshowen less " + this.iTimeLimit + "ms");
            }
            this.mWebAdClicked = false;
        }
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void SetPosition(EditText v, int l, int t, int w, int h) {
        int singleLineHeight;
        Log.w("BicoreActivity", "*SetPosition : " + v.toString() + "(" + l + "," + t + "," + w + "," + h + ")");
        if (h < 50) {
            singleLineHeight = 50;
        } else {
            singleLineHeight = h;
        }
        this.mEditparam.leftMargin = ((BicoreActivity.iScreenWidth * l) / BicoreActivity.iContentsWidth) - 1;
        this.mEditparam.topMargin = ((BicoreActivity.iScreenHeight * t) / BicoreActivity.iContentsHeight) + 1;
        if (singleLineHeight > h) {
            this.mEditparam.topMargin -= (singleLineHeight - h) / 2;
        }
        this.mEditparam.width = ((BicoreActivity.iScreenWidth * w) / BicoreActivity.iContentsWidth) + 2;
        this.mEditparam.height = singleLineHeight;
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public int GetMaxTextSize(EditText v) {
        return 255;
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public String GetText(EditText v) {
        return v.getText().toString();
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public int GetTextSize(EditText v) {
        return v.getText().toString().getBytes().length;
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void SetMaxTextSize(EditText v, int size) {
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void DeleteText(EditText v, int start2, int length2) {
        this.start = start2;
        this.length = length2;
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void InsertText(EditText v, String s, int start2, int length2) {
        this.start = start2;
        this.length = length2;
        this.strInsertText = s.toString();
    }

    @Override // com.bicore.NativeFuntion.FrameEventListener
    public void SetInputType(boolean numberlic, boolean email, boolean password) {
        int newType;
        if (numberlic) {
            newType = 0 | 2;
        } else {
            newType = 0 | 1;
        }
        if (email) {
            newType |= 32;
        }
        if (password) {
            newType |= 128;
        }
        this.EditBoxInputType = newType;
    }
}
