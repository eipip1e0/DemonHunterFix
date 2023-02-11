package com.google.gson;

import com.bicore.dhunter.R;
import com.feelingk.iap.util.Defines;

/* access modifiers changed from: package-private */
public final class ba extends Error {
    int a;

    public ba() {
    }

    private ba(String str) {
        super(str);
        this.a = 0;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public ba(boolean z, int i, int i2, String str, char c) {
        this("Lexical error at line " + i + ", column " + i2 + ".  Encountered: " + (z ? "<EOF> " : "\"" + a(String.valueOf(c)) + "\"" + " (" + ((int) c) + "), ") + "after : \"" + a(str) + "\"");
    }

    private static String a(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            switch (str.charAt(i)) {
                case 0:
                    break;
                case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
                    stringBuffer.append("\\b");
                    break;
                case '\t':
                    stringBuffer.append("\\t");
                    break;
                case '\n':
                    stringBuffer.append("\\n");
                    break;
                case Defines.IAP_PACKET_HEADER_SIZE:
                    stringBuffer.append("\\f");
                    break;
                case '\r':
                    stringBuffer.append("\\r");
                    break;
                case '\"':
                    stringBuffer.append("\\\"");
                    break;
                case '\'':
                    stringBuffer.append("\\'");
                    break;
                case '\\':
                    stringBuffer.append("\\\\");
                    break;
                default:
                    char charAt = str.charAt(i);
                    if (charAt < ' ' || charAt > '~') {
                        String str2 = "0000" + Integer.toString(charAt, 16);
                        stringBuffer.append("\\u" + str2.substring(str2.length() - 4, str2.length()));
                        break;
                    } else {
                        stringBuffer.append(charAt);
                        break;
                    }
                    break;
            }
        }
        return stringBuffer.toString();
    }

    public final String getMessage() {
        return super.getMessage();
    }
}
