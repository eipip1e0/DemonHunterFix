package com.google.gson;

import com.bicore.dhunter.R;
import com.feelingk.iap.util.Defines;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/* access modifiers changed from: package-private */
public final class h {
    private static final char[] a = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    private static final Set<Character> b;
    private static final Set<Character> c;
    private final boolean d;

    static {
        HashSet hashSet = new HashSet();
        hashSet.add('\"');
        hashSet.add('\\');
        b = Collections.unmodifiableSet(hashSet);
        HashSet hashSet2 = new HashSet();
        hashSet2.add('<');
        hashSet2.add('>');
        hashSet2.add('&');
        hashSet2.add('=');
        hashSet2.add('\'');
        c = Collections.unmodifiableSet(hashSet2);
    }

    h(boolean z) {
        this.d = z;
    }

    private static void a(int i, Appendable appendable) throws IOException {
        char c2 = i;
        while (Character.isSupplementaryCodePoint(c2 == 1 ? 1 : 0)) {
            char[] chars = Character.toChars(c2);
            a(chars[0], appendable);
            c2 = chars[1];
        }
        appendable.append("\\u").append(a[(c2 >>> 12) & 15]).append(a[(c2 >>> 8) & 15]).append(a[(c2 >>> 4) & 15]).append(a[(c2 == 1 ? 1 : 0) & 15]);
    }

    private void a(CharSequence charSequence, StringBuffer stringBuffer) throws IOException {
        boolean z;
        int length = charSequence.length();
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int codePointAt = Character.codePointAt(charSequence, i);
            int charCount = Character.charCount(codePointAt);
            if (!(codePointAt < 32 || codePointAt == 8232 || codePointAt == 8233 || (codePointAt >= 127 && codePointAt <= 159))) {
                if (!Character.isSupplementaryCodePoint(codePointAt)) {
                    char c2 = (char) codePointAt;
                    z = b.contains(Character.valueOf(c2)) || (this.d && c.contains(Character.valueOf(c2)));
                } else {
                    z = false;
                }
                if (!z) {
                    i += charCount;
                }
            }
            stringBuffer.append(charSequence, i2, i);
            i2 = i + charCount;
            switch (codePointAt) {
                case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
                    stringBuffer.append("\\b");
                    continue;
                case 9:
                    stringBuffer.append("\\t");
                    continue;
                case 10:
                    stringBuffer.append("\\n");
                    continue;
                case Defines.IAP_PACKET_HEADER_SIZE:
                    stringBuffer.append("\\f");
                    continue;
                case 13:
                    stringBuffer.append("\\r");
                    continue;
                case 34:
                    stringBuffer.append("\\\"");
                    continue;
                case 47:
                    stringBuffer.append("\\/");
                    continue;
                case 92:
                    stringBuffer.append("\\\\");
                    continue;
                default:
                    a(codePointAt, stringBuffer);
                    continue;
            }
            i += charCount;
        }
        stringBuffer.append(charSequence, i2, length);
    }

    public final String a(CharSequence charSequence) {
        StringBuffer stringBuffer = new StringBuffer(charSequence.length() + 20);
        try {
            a(charSequence, stringBuffer);
            return stringBuffer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
