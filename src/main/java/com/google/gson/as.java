package com.google.gson;

import com.bicore.dhunter.R;
import com.feelingk.iap.util.Defines;

/* access modifiers changed from: package-private */
public final class as extends Exception {
    public az a;
    public int[][] b;
    public String[] c;
    protected String d = System.getProperty("line.separator", "\n");

    public as() {
    }

    public as(az azVar, int[][] iArr, String[] strArr) {
        super(a(azVar, iArr, strArr));
        this.a = azVar;
        this.b = iArr;
        this.c = strArr;
    }

    private static String a(az azVar, int[][] iArr, String[] strArr) {
        String str;
        String property = System.getProperty("line.separator", "\n");
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i < iArr[i2].length) {
                i = iArr[i2].length;
            }
            for (int i3 = 0; i3 < iArr[i2].length; i3++) {
                stringBuffer.append(strArr[iArr[i2][i3]]).append(' ');
            }
            if (iArr[i2][iArr[i2].length - 1] != 0) {
                stringBuffer.append("...");
            }
            stringBuffer.append(property).append("    ");
        }
        az azVar2 = azVar.g;
        String str2 = "Encountered \"";
        int i4 = 0;
        while (true) {
            if (i4 >= i) {
                str = str2;
                break;
            }
            if (i4 != 0) {
                str2 = str2 + " ";
            }
            if (azVar2.a == 0) {
                str = str2 + strArr[0];
                break;
            }
            str2 = (((str2 + " " + strArr[azVar2.a]) + " \"") + a(azVar2.f)) + " \"";
            azVar2 = azVar2.g;
            i4++;
        }
        String str3 = (str + "\" at line " + azVar.g.b + ", column " + azVar.g.c) + "." + property;
        return (iArr.length == 1 ? str3 + "Was expecting:" + property + "    " : str3 + "Was expecting one of:" + property + "    ") + stringBuffer.toString();
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
}
