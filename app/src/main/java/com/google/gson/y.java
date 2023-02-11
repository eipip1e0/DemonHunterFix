package com.google.gson;

import com.bicore.dhunter.R;
import com.feelingk.iap.util.Defines;
import java.io.EOFException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* access modifiers changed from: package-private */
public final class y implements z {
    private static int[] l = {67108864, -2013067328, -1945958463, 268435456, 202752, 202752, 268435456, -2013067328, -2013067328, 198464, 0, 32, 64, 0, 768, 0, 6144, 196608};
    private static int[] m = {0, 2, 2, 0, 0, 0, 0, 3, 2, 2, 4, 0, 2, 2, 2, 2, 0, 0};
    public aa a;
    ax b;
    public az c;
    public az d;
    private int f;
    private az g;
    private az h;
    private int i;
    private int j;
    private final int[] k = new int[18];
    private final a[] n = new a[1];
    private boolean o = false;
    private int p = 0;
    private final b q = new b();
    private List r = new ArrayList();
    private int[] s;
    private int t = -1;
    private int[] u = new int[100];
    private int v;

    /* access modifiers changed from: package-private */
    public static final class a {
        int a;
        az b;
        int c;
        a d;

        a() {
        }
    }

    /* access modifiers changed from: private */
    public static final class b extends Error {
        /* synthetic */ b() {
            this((byte) 0);
        }

        private b(byte b) {
        }
    }

    public y(Reader reader) {
        this.b = new ax(reader);
        this.a = new aa(this.b);
        this.c = new az();
        this.f = -1;
        this.j = 0;
        for (int i2 = 0; i2 < 18; i2++) {
            this.k[i2] = -1;
        }
        for (int i3 = 0; i3 < this.n.length; i3++) {
            this.n[i3] = new a();
        }
    }

    private az a(int i2) throws as {
        az azVar = this.c;
        if (azVar.g != null) {
            this.c = this.c.g;
        } else {
            az azVar2 = this.c;
            az a2 = this.a.a();
            azVar2.g = a2;
            this.c = a2;
        }
        this.f = -1;
        if (this.c.a == i2) {
            this.j++;
            int i3 = this.p + 1;
            this.p = i3;
            if (i3 > 100) {
                this.p = 0;
                for (int i4 = 0; i4 < this.n.length; i4++) {
                    for (a aVar = this.n[i4]; aVar != null; aVar = aVar.d) {
                        if (aVar.a < this.j) {
                            aVar.b = null;
                        }
                    }
                }
            }
            return this.c;
        }
        this.c = azVar;
        this.t = i2;
        this.r.clear();
        boolean[] zArr = new boolean[35];
        if (this.t >= 0) {
            zArr[this.t] = true;
            this.t = -1;
        }
        for (int i5 = 0; i5 < 18; i5++) {
            if (this.k[i5] == this.j) {
                for (int i6 = 0; i6 < 32; i6++) {
                    if ((l[i5] & (1 << i6)) != 0) {
                        zArr[i6] = true;
                    }
                    if ((m[i5] & (1 << i6)) != 0) {
                        zArr[i6 + 32] = true;
                    }
                }
            }
        }
        for (int i7 = 0; i7 < 35; i7++) {
            if (zArr[i7]) {
                this.s = new int[1];
                this.s[0] = i7;
                this.r.add(this.s);
            }
        }
        this.v = 0;
        this.o = true;
        for (int i8 = 0; i8 <= 0; i8++) {
            try {
                a aVar2 = this.n[i8];
                do {
                    if (aVar2.a > this.j) {
                        this.i = aVar2.c;
                        az azVar3 = aVar2.b;
                        this.g = azVar3;
                        this.h = azVar3;
                        switch (i8) {
                            case 0:
                                k();
                                break;
                        }
                    }
                    aVar2 = aVar2.d;
                } while (aVar2 != null);
            } catch (b e) {
            }
        }
        this.o = false;
        a(0, 0);
        int[][] iArr = new int[this.r.size()][];
        for (int i9 = 0; i9 < this.r.size(); i9++) {
            iArr[i9] = (int[]) this.r.get(i9);
        }
        throw new as(this.c, iArr, e);
    }

    private void a(int i2, int i3) {
        if (i3 < 100) {
            if (i3 == this.v + 1) {
                int[] iArr = this.u;
                int i4 = this.v;
                this.v = i4 + 1;
                iArr[i4] = i2;
            } else if (this.v != 0) {
                this.s = new int[this.v];
                for (int i5 = 0; i5 < this.v; i5++) {
                    this.s[i5] = this.u[i5];
                }
                Iterator it = this.r.iterator();
                loop1:
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    int[] iArr2 = (int[]) it.next();
                    if (iArr2.length == this.s.length) {
                        for (int i6 = 0; i6 < this.s.length; i6++) {
                            if (iArr2[i6] == this.s[i6]) {
                            }
                        }
                        this.r.add(this.s);
                        break loop1;
                    }
                }
                if (i3 != 0) {
                    int[] iArr3 = this.u;
                    this.v = i3;
                    iArr3[i3 - 1] = i2;
                }
            }
        }
    }

    private final JsonObject b() throws as {
        JsonPrimitive i2;
        az a2;
        JsonObject jsonObject = new JsonObject();
        a(27);
        switch (this.f == -1 ? l() : this.f) {
            case 11:
            case Defines.IAP_PACKET_HEADER_SIZE:
            case 16:
            case 17:
                while (true) {
                    switch (this.f == -1 ? l() : this.f) {
                        case 11:
                        case Defines.IAP_PACKET_HEADER_SIZE:
                            switch (this.f == -1 ? l() : this.f) {
                                case 11:
                                    a2 = a(11);
                                    break;
                                case Defines.IAP_PACKET_HEADER_SIZE:
                                    a2 = a(12);
                                    break;
                                default:
                                    this.k[16] = this.j;
                                    a(-1);
                                    throw new as();
                            }
                            i2 = new JsonPrimitive(a2.f);
                            break;
                        case 13:
                        case 14:
                        case 15:
                        default:
                            this.k[5] = this.j;
                            a(-1);
                            throw new as();
                        case 16:
                        case 17:
                            i2 = i();
                            break;
                    }
                    a(30);
                    jsonObject.add(i2.getAsString(), e());
                    switch (this.f == -1 ? l() : this.f) {
                        case 28:
                            a(28);
                        default:
                            this.k[3] = this.j;
                            break;
                    }
                }
                a(29);
                return jsonObject;
            case 13:
            case 14:
            case 15:
            default:
                this.k[4] = this.j;
                a(29);
                return jsonObject;
        }
    }

    private boolean b(int i2) {
        if (this.g == this.h) {
            this.i--;
            if (this.g.g == null) {
                az azVar = this.g;
                az a2 = this.a.a();
                azVar.g = a2;
                this.g = a2;
                this.h = a2;
            } else {
                az azVar2 = this.g.g;
                this.g = azVar2;
                this.h = azVar2;
            }
        } else {
            this.g = this.g.g;
        }
        if (this.o) {
            az azVar3 = this.c;
            int i3 = 0;
            while (azVar3 != null && azVar3 != this.g) {
                i3++;
                azVar3 = azVar3.g;
            }
            if (azVar3 != null) {
                a(i2, i3);
            }
        }
        if (this.g.a != i2) {
            return true;
        }
        if (this.i != 0 || this.g != this.h) {
            return false;
        }
        throw this.q;
    }

    private final JsonNull c() throws as {
        a(7);
        return JsonNull.b();
    }

    private final JsonArray d() throws as {
        JsonArray jsonArray = new JsonArray();
        a(31);
        switch (this.f == -1 ? l() : this.f) {
            case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
            case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
            case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
            case 9:
            case 10:
            case 16:
            case 17:
            case 27:
            case 31:
            case 33:
                jsonArray.add(e());
                while (true) {
                    switch (this.f == -1 ? l() : this.f) {
                        case 28:
                            a(28);
                            jsonArray.add(e());
                        default:
                            this.k[6] = this.j;
                            a(32);
                            break;
                    }
                }
            case 32:
                a(32);
                break;
            default:
                this.k[7] = this.j;
                a(-1);
                throw new as();
        }
        return jsonArray;
    }

    private final JsonElement e() throws as {
        switch (this.f == -1 ? l() : this.f) {
            case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
            case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
            case 9:
            case 33:
                return g();
            case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
                return c();
            case 10:
                return f();
            case 16:
            case 17:
                return i();
            case 27:
                return b();
            case 31:
                return d();
            default:
                this.k[8] = this.j;
                a(-1);
                throw new as();
        }
    }

    private final JsonPrimitive f() throws as {
        return new JsonPrimitive(Boolean.valueOf(Boolean.valueOf(a(10).f).booleanValue()));
    }

    private final JsonPrimitive g() throws as {
        boolean z;
        String str;
        String str2;
        BigInteger bigInteger;
        boolean z2;
        if (j()) {
            switch (this.f == -1 ? l() : this.f) {
                case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
                    a(8);
                    return new JsonPrimitive((Number) Double.valueOf(Double.NaN));
                case 9:
                case 33:
                    switch (this.f == -1 ? l() : this.f) {
                        case 33:
                            a(33);
                            z2 = true;
                            break;
                        default:
                            this.k[13] = this.j;
                            z2 = false;
                            break;
                    }
                    a(9);
                    return new JsonPrimitive((Number) Double.valueOf(z2 ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY));
                default:
                    this.k[14] = this.j;
                    a(-1);
                    throw new as();
            }
        } else {
            switch (this.f == -1 ? l() : this.f) {
                case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                case 33:
                    switch (this.f == -1 ? l() : this.f) {
                        case 33:
                            a(33);
                            z = true;
                            break;
                        default:
                            this.k[15] = this.j;
                            z = false;
                            break;
                    }
                    String h2 = h();
                    String str3 = z ? "-" + h2 : h2;
                    switch (this.f == -1 ? l() : this.f) {
                        case 34:
                            a(34);
                            str = "." + h();
                            break;
                        default:
                            this.k[10] = this.j;
                            str = null;
                            break;
                    }
                    switch (this.f == -1 ? l() : this.f) {
                        case 5:
                            str2 = a(5).f;
                            break;
                        default:
                            this.k[11] = this.j;
                            str2 = null;
                            break;
                    }
                    if (str2 == null && str == null) {
                        bigInteger = new BigInteger(str3);
                    } else {
                        if (str == null) {
                            str = "";
                        }
                        if (str2 == null) {
                            str2 = "";
                        }
                        bigInteger = new BigDecimal(str3 + str + str2);
                    }
                    return new JsonPrimitive(bigInteger);
                default:
                    this.k[12] = this.j;
                    a(-1);
                    throw new as();
            }
        }
    }

    private final String h() throws as {
        return a(6).f;
    }

    private final JsonPrimitive i() throws as {
        az a2;
        char c2;
        switch (this.f == -1 ? l() : this.f) {
            case 16:
                a2 = a(16);
                break;
            case 17:
                a2 = a(17);
                break;
            default:
                this.k[17] = this.j;
                a(-1);
                throw new as();
        }
        String str = a2.f;
        String substring = str.substring(1, str.length() - 1);
        int length = substring.length();
        StringBuilder sb = new StringBuilder(length);
        int i2 = 0;
        while (i2 < length) {
            char charAt = substring.charAt(i2);
            i2++;
            if (charAt == '\\') {
                char charAt2 = substring.charAt(i2);
                i2++;
                if (charAt2 == 'u') {
                    sb.appendCodePoint(Integer.parseInt(substring.substring(i2, i2 + 4), 16));
                    i2 += 4;
                } else {
                    switch (charAt2) {
                        case '\"':
                            c2 = '\"';
                            break;
                        case '\'':
                            c2 = '\'';
                            break;
                        case '/':
                            c2 = '/';
                            break;
                        case '\\':
                            c2 = '\\';
                            break;
                        case 'b':
                            c2 = '\b';
                            break;
                        case Defines.DIALOG_STATE.DLG_LOADING_PROGRESS:
                            c2 = '\f';
                            break;
                        case 'n':
                            c2 = '\n';
                            break;
                        case 'r':
                            c2 = '\r';
                            break;
                        case 't':
                            c2 = '\t';
                            break;
                        default:
                            throw new IllegalStateException("Unexpected character: " + charAt2 + " in " + substring);
                    }
                    sb.append(c2);
                }
            } else {
                sb.append(charAt);
            }
        }
        return new JsonPrimitive(sb.toString());
    }

    private boolean j() {
        this.i = 2;
        az azVar = this.c;
        this.g = azVar;
        this.h = azVar;
        try {
            return !k();
        } catch (b e) {
            return true;
        } finally {
            m();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0038  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private boolean k() {
        /*
            r4 = this;
            r3 = 1
            r2 = 0
            com.google.gson.az r0 = r4.g
            r1 = 8
            boolean r1 = r4.b(r1)
            if (r1 == 0) goto L_0x0030
            r1 = r3
        L_0x000d:
            if (r1 == 0) goto L_0x0036
            r4.g = r0
            com.google.gson.az r0 = r4.g
            r1 = 33
            boolean r1 = r4.b(r1)
            if (r1 == 0) goto L_0x0032
            r1 = r3
        L_0x001c:
            if (r1 == 0) goto L_0x0020
            r4.g = r0
        L_0x0020:
            r0 = 9
            boolean r0 = r4.b(r0)
            if (r0 == 0) goto L_0x0034
            r0 = r3
        L_0x0029:
            if (r0 == 0) goto L_0x0036
            r0 = r3
        L_0x002c:
            if (r0 == 0) goto L_0x0038
            r0 = r3
        L_0x002f:
            return r0
        L_0x0030:
            r1 = r2
            goto L_0x000d
        L_0x0032:
            r1 = r2
            goto L_0x001c
        L_0x0034:
            r0 = r2
            goto L_0x0029
        L_0x0036:
            r0 = r2
            goto L_0x002c
        L_0x0038:
            r0 = r2
            goto L_0x002f
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.y.k():boolean");
    }

    private int l() {
        az azVar = this.c.g;
        this.d = azVar;
        if (azVar == null) {
            az azVar2 = this.c;
            az a2 = this.a.a();
            azVar2.g = a2;
            int i2 = a2.a;
            this.f = i2;
            return i2;
        }
        int i3 = this.d.a;
        this.f = i3;
        return i3;
    }

    private void m() {
        a aVar = this.n[0];
        while (true) {
            if (aVar.a <= this.j) {
                break;
            } else if (aVar.d == null) {
                a aVar2 = new a();
                aVar.d = aVar2;
                aVar = aVar2;
                break;
            } else {
                aVar = aVar.d;
            }
        }
        aVar.a = (this.j + 2) - this.i;
        aVar.b = this.c;
        aVar.c = 2;
    }

    public final JsonElement a() throws as {
        switch (this.f == -1 ? l() : this.f) {
            case 0:
                a(0);
                throw new JsonParseException(new EOFException());
            case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
            case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
            case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
            case 9:
            case 10:
            case 16:
            case 17:
            case 26:
            case 27:
            case 31:
            case 33:
                switch (this.f == -1 ? l() : this.f) {
                    case 26:
                        a(26);
                        break;
                    default:
                        this.k[0] = this.j;
                        break;
                }
                switch (this.f == -1 ? l() : this.f) {
                    case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                    case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
                    case 9:
                    case 10:
                    case 16:
                    case 17:
                    case 33:
                        switch (this.f == -1 ? l() : this.f) {
                            case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                            case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
                            case 9:
                            case 33:
                                return g();
                            case 10:
                                return f();
                            case 16:
                            case 17:
                                return i();
                            default:
                                this.k[9] = this.j;
                                a(-1);
                                throw new as();
                        }
                    case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
                        return c();
                    case 27:
                        return b();
                    case 31:
                        return d();
                    default:
                        this.k[1] = this.j;
                        a(-1);
                        throw new as();
                }
            default:
                this.k[2] = this.j;
                a(-1);
                throw new as();
        }
    }
}
