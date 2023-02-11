package com.google.gson;

import com.bicore.dhunter.R;
import com.feelingk.iap.util.Defines;
import java.io.IOException;
import java.io.PrintStream;

/* access modifiers changed from: package-private */
public final class aa implements z {
    static final long[] b = {-2, -1, -1, -1};
    static final long[] c = {0, 0, -1, -1};
    static final int[] d = {29, 30, 37, 38, 18, 19, 26, 27};
    public static final String[] f = {"", null, null, null, null, null, null, "null", "NaN", "Infinity", null, null, null, null, null, null, null, null, "\"", null, null, null, null, null, null, null, ")]}'\n", "{", ",", "}", ":", "[", "]", "-", "."};
    public static final String[] g = {"DEFAULT", "STRING_STATE", "ESC_STATE", "HEX_STATE"};
    public static final int[] h = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 2, 0, -1, 1, 3, -1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
    static final long[] i = {34334007265L};
    static final long[] j = {30};
    static final long[] k = {8912896};
    public PrintStream a = System.out;
    protected ax l;
    protected char m;
    int n = 0;
    int o = 0;
    int p;
    int q;
    int r;
    int s;
    private final int[] t = new int[43];
    private final int[] u = new int[86];

    public aa(ax axVar) {
        this.l = axVar;
    }

    private int a(int i2) {
        this.p = 4;
        int i3 = 1;
        this.u[0] = 0;
        int i4 = i2;
        int i5 = Integer.MAX_VALUE;
        int i6 = 0;
        while (true) {
            int i7 = this.q + 1;
            this.q = i7;
            if (i7 == Integer.MAX_VALUE) {
                b();
            }
            if (this.m < '@') {
                long j2 = 1 << this.m;
                do {
                    i3--;
                    switch (this.u[i3]) {
                        case 0:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr = this.u;
                                int i8 = this.p;
                                this.p = i8 + 1;
                                iArr[i8] = 1;
                                continue;
                            } else {
                                continue;
                            }
                        case 1:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr2 = this.u;
                                int i9 = this.p;
                                this.p = i9 + 1;
                                iArr2[i9] = 2;
                                continue;
                            } else {
                                continue;
                            }
                        case 2:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr3 = this.u;
                                int i10 = this.p;
                                this.p = i10 + 1;
                                iArr3[i10] = 3;
                                continue;
                            } else {
                                continue;
                            }
                        case 3:
                            if ((287948901175001088L & j2) != 0 && i5 > 25) {
                                i5 = 25;
                                continue;
                            }
                    }
                } while (i3 != i6);
            } else if (this.m < 128) {
                long j3 = 1 << (this.m & '?');
                do {
                    i3--;
                    switch (this.u[i3]) {
                        case 0:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr4 = this.u;
                                int i11 = this.p;
                                this.p = i11 + 1;
                                iArr4[i11] = 1;
                                continue;
                            } else {
                                continue;
                            }
                        case 1:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr5 = this.u;
                                int i12 = this.p;
                                this.p = i12 + 1;
                                iArr5[i12] = 2;
                                continue;
                            } else {
                                continue;
                            }
                        case 2:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr6 = this.u;
                                int i13 = this.p;
                                this.p = i13 + 1;
                                iArr6[i13] = 3;
                                continue;
                            } else {
                                continue;
                            }
                        case 3:
                            if ((541165879422L & j3) != 0 && i5 > 25) {
                                i5 = 25;
                                continue;
                            }
                    }
                } while (i3 != i6);
            } else {
                char c2 = this.m;
                char c3 = this.m;
                char c4 = this.m;
                do {
                    int[] iArr7 = this.u;
                    i3--;
                } while (i3 != i6);
            }
            if (i5 != Integer.MAX_VALUE) {
                this.s = i5;
                this.r = i4;
                i5 = Integer.MAX_VALUE;
            }
            int i14 = i4 + 1;
            int i15 = this.p;
            this.p = i6;
            i6 = 4 - i6;
            if (i15 == i6) {
                return i14;
            }
            try {
                this.m = this.l.b();
                i4 = i14;
                i3 = i15;
            } catch (IOException e) {
                return i14;
            }
        }
    }

    private int a(int i2, int i3) {
        this.s = i3;
        this.r = i2;
        return i2 + 1;
    }

    private int a(int i2, int i3, int i4) {
        this.s = i3;
        this.r = i2;
        try {
            this.m = this.l.b();
            return b(i4, i2 + 1);
        } catch (IOException e) {
            return i2 + 1;
        }
    }

    private final int a(int i2, long j2) {
        switch (i2) {
            case 0:
                if ((896 & j2) == 0) {
                    return (262144 & j2) != 0 ? 43 : -1;
                }
                this.s = 11;
                return 10;
            case 1:
                if ((896 & j2) == 0) {
                    return -1;
                }
                this.s = 11;
                this.r = 1;
                return 10;
            case 2:
                if ((256 & j2) != 0) {
                    return 10;
                }
                if ((640 & j2) == 0) {
                    return -1;
                }
                this.s = 11;
                this.r = 2;
                return 10;
            case 3:
                if ((128 & j2) != 0) {
                    return 10;
                }
                if ((j2 & 512) == 0) {
                    return -1;
                }
                this.s = 11;
                this.r = 3;
                return 10;
            case 4:
                if ((j2 & 512) == 0) {
                    return -1;
                }
                this.s = 11;
                this.r = 4;
                return 10;
            case 5:
                if ((j2 & 512) == 0) {
                    return -1;
                }
                this.s = 11;
                this.r = 5;
                return 10;
            case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                if ((j2 & 512) == 0) {
                    return -1;
                }
                this.s = 11;
                this.r = 6;
                return 10;
            default:
                return -1;
        }
    }

    private int a(long j2) {
        try {
            this.m = this.l.b();
            switch (this.m) {
                case ']':
                    return a(j2, 67108864);
                case 'a':
                    return a(j2, 256);
                case 'n':
                    return a(j2, 512);
                case 'u':
                    return a(j2, 128);
                default:
                    return b(0, j2);
            }
        } catch (IOException e) {
            a(0, j2);
            return 1;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private int a(long j2, long j3) {
        long j4 = j3 & j2;
        if (j4 == 0) {
            return b(0, j2);
        }
        try {
            this.m = this.l.b();
            switch (this.m) {
                case 'N':
                    if ((256 & j4) != 0) {
                        return a(2, 8, 10);
                    }
                    break;
                case Defines.DIALOG_STATE.DLG_LOADING_PROGRESS:
                    return b(j4, 512);
                case 'l':
                    return b(j4, 128);
                case '}':
                    return b(j4, 67108864);
            }
            return b(1, j4);
        } catch (IOException e) {
            a(1, j4);
            return 2;
        }
    }

    private static final boolean a(int i2, int i3, int i4, long j2, long j3) {
        switch (i2) {
            case 0:
                return (c[i4] & j3) != 0;
            default:
                return (b[i3] & j2) != 0;
        }
    }

    private int b(int i2) {
        this.p = 1;
        this.u[0] = 0;
        int i3 = Integer.MAX_VALUE;
        int i4 = 0;
        int i5 = i2;
        int i6 = 1;
        while (true) {
            int i7 = this.q + 1;
            this.q = i7;
            if (i7 == Integer.MAX_VALUE) {
                b();
            }
            if (this.m < '@') {
                long j2 = 1 << this.m;
                do {
                    i6--;
                    switch (this.u[i6]) {
                        case 0:
                            if ((140754668224512L & j2) != 0) {
                                i3 = 22;
                                continue;
                            } else {
                                continue;
                            }
                    }
                } while (i6 != i4);
            } else if (this.m < 128) {
                long j3 = 1 << (this.m & '?');
                do {
                    i6--;
                    switch (this.u[i6]) {
                        case 0:
                            if ((5700160604602368L & j3) != 0) {
                                i3 = 22;
                                continue;
                            } else {
                                continue;
                            }
                    }
                } while (i6 != i4);
            } else {
                char c2 = this.m;
                char c3 = this.m;
                char c4 = this.m;
                do {
                    int[] iArr = this.u;
                    i6--;
                } while (i6 != i4);
            }
            if (i3 != Integer.MAX_VALUE) {
                this.s = i3;
                this.r = i5;
                i3 = Integer.MAX_VALUE;
            }
            int i8 = i5 + 1;
            int i9 = this.p;
            this.p = i4;
            i4 = 1 - i4;
            if (i9 == i4) {
                return i8;
            }
            try {
                this.m = this.l.b();
                i5 = i8;
                i6 = i9;
            } catch (IOException e) {
                return i8;
            }
        }
    }

    private int b(int i2, int i3) {
        int i4;
        this.p = 43;
        this.u[0] = i2;
        int i5 = 1;
        int i6 = 0;
        int i7 = i3;
        int i8 = Integer.MAX_VALUE;
        while (true) {
            int i9 = this.q + 1;
            this.q = i9;
            if (i9 == Integer.MAX_VALUE) {
                b();
            }
            if (this.m < '@') {
                long j2 = 1 << this.m;
                int i10 = i5;
                int i11 = i8;
                do {
                    i10--;
                    switch (this.u[i10]) {
                        case 0:
                            if ((287948901175001088L & j2) == 0) {
                                continue;
                            } else {
                                if (i11 > 6) {
                                    i11 = 6;
                                }
                                d(0);
                                continue;
                            }
                        case 4:
                            if ((287948901175001088L & j2) != 0) {
                                if (i11 > 6) {
                                    i11 = 6;
                                }
                                d(0);
                                continue;
                            } else if (this.m == '\"') {
                                c(0, 3);
                                continue;
                            } else if (this.m == '\'') {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 10:
                            if ((287948901175001088L & j2) == 0) {
                                continue;
                            } else {
                                if (i11 > 11) {
                                    i11 = 11;
                                }
                                int[] iArr = this.u;
                                int i12 = this.p;
                                this.p = i12 + 1;
                                iArr[i12] = 10;
                                continue;
                            }
                        case Defines.IAP_PACKET_HEADER_SIZE:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr2 = this.u;
                                int i13 = this.p;
                                this.p = i13 + 1;
                                iArr2[i13] = 13;
                                continue;
                            } else {
                                continue;
                            }
                        case 13:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr3 = this.u;
                                int i14 = this.p;
                                this.p = i14 + 1;
                                iArr3[i14] = 14;
                                continue;
                            } else {
                                continue;
                            }
                        case 14:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr4 = this.u;
                                int i15 = this.p;
                                this.p = i15 + 1;
                                iArr4[i15] = 15;
                                continue;
                            } else {
                                continue;
                            }
                        case 15:
                            if ((287948901175001088L & j2) != 0 && i11 > 14) {
                                i11 = 14;
                                continue;
                            }
                        case 17:
                            if (this.m == '\'') {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 18:
                            if ((-549755823105L & j2) != 0) {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 20:
                            if ((141304424038400L & j2) != 0) {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 22:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr5 = this.u;
                                int i16 = this.p;
                                this.p = i16 + 1;
                                iArr5[i16] = 23;
                                continue;
                            } else {
                                continue;
                            }
                        case 23:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr6 = this.u;
                                int i17 = this.p;
                                this.p = i17 + 1;
                                iArr6[i17] = 24;
                                continue;
                            } else {
                                continue;
                            }
                        case 24:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr7 = this.u;
                                int i18 = this.p;
                                this.p = i18 + 1;
                                iArr7[i18] = 25;
                                continue;
                            } else {
                                continue;
                            }
                        case 25:
                            if ((287948901175001088L & j2) != 0) {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 27:
                            if (this.m == '\'' && i11 > 16) {
                                i11 = 16;
                                continue;
                            }
                        case 28:
                            if (this.m == '\"') {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 29:
                            if ((-17179878401L & j2) != 0) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 31:
                            if ((141304424038400L & j2) != 0) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 33:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr8 = this.u;
                                int i19 = this.p;
                                this.p = i19 + 1;
                                iArr8[i19] = 34;
                                continue;
                            } else {
                                continue;
                            }
                        case 34:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr9 = this.u;
                                int i20 = this.p;
                                this.p = i20 + 1;
                                iArr9[i20] = 35;
                                continue;
                            } else {
                                continue;
                            }
                        case 35:
                            if ((287948901175001088L & j2) != 0) {
                                int[] iArr10 = this.u;
                                int i21 = this.p;
                                this.p = i21 + 1;
                                iArr10[i21] = 36;
                                continue;
                            } else {
                                continue;
                            }
                        case 36:
                            if ((287948901175001088L & j2) != 0) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 38:
                            if (this.m == '\"' && i11 > 17) {
                                i11 = 17;
                                continue;
                            }
                        case 40:
                            if ((287992881640112128L & j2) == 0) {
                                continue;
                            } else {
                                if (i11 > 5) {
                                    i11 = 5;
                                }
                                d(41);
                                continue;
                            }
                        case 41:
                            if ((287948901175001088L & j2) == 0) {
                                continue;
                            } else {
                                if (i11 > 5) {
                                    i11 = 5;
                                }
                                d(41);
                                continue;
                            }
                        case 42:
                            if ((287948901175001088L & j2) == 0) {
                                continue;
                            } else {
                                if (i11 > 12) {
                                    i11 = 12;
                                }
                                int[] iArr11 = this.u;
                                int i22 = this.p;
                                this.p = i22 + 1;
                                iArr11[i22] = 42;
                                continue;
                            }
                        case 43:
                            if ((-17179878401L & j2) != 0) {
                                c(0, 3);
                                continue;
                            } else if (this.m == '\"' && i11 > 17) {
                                i11 = 17;
                                continue;
                            }
                    }
                } while (i10 != i6);
                i4 = i11;
            } else if (this.m < 128) {
                long j3 = 1 << (this.m & '?');
                int i23 = i5;
                int i24 = i8;
                do {
                    i23--;
                    switch (this.u[i23]) {
                        case 1:
                            if (this.m == 'e' && i24 > 10) {
                                i24 = 10;
                                continue;
                            }
                        case 2:
                            if (this.m == 'u') {
                                d(1);
                                continue;
                            } else {
                                continue;
                            }
                        case 3:
                            if (this.m == 'r') {
                                int[] iArr12 = this.u;
                                int i25 = this.p;
                                this.p = i25 + 1;
                                iArr12[i25] = 2;
                                continue;
                            } else {
                                continue;
                            }
                        case 4:
                            if ((576460608556236766L & j3) != 0) {
                                if (i24 > 11) {
                                    i24 = 11;
                                }
                                d(10);
                            } else if ((137438953504L & j3) != 0) {
                                if (i24 > 12) {
                                    i24 = 12;
                                }
                                d();
                            } else if (this.m == '\\') {
                                int[] iArr13 = this.u;
                                int i26 = this.p;
                                this.p = i26 + 1;
                                iArr13[i26] = 11;
                            }
                            if (this.m == 'f') {
                                int[] iArr14 = this.u;
                                int i27 = this.p;
                                this.p = i27 + 1;
                                iArr14[i27] = 7;
                                continue;
                            } else if (this.m == 't') {
                                int[] iArr15 = this.u;
                                int i28 = this.p;
                                this.p = i28 + 1;
                                iArr15[i28] = 3;
                                continue;
                            } else {
                                continue;
                            }
                        case 5:
                            if (this.m == 's') {
                                d(1);
                                continue;
                            } else {
                                continue;
                            }
                        case R.styleable.com_cauly_android_ad_AdView_gps /*{ENCODED_INT: 6}*/:
                            if (this.m == 'l') {
                                int[] iArr16 = this.u;
                                int i29 = this.p;
                                this.p = i29 + 1;
                                iArr16[i29] = 5;
                                continue;
                            } else {
                                continue;
                            }
                        case R.styleable.com_cauly_android_ad_AdView_effect /*{ENCODED_INT: 7}*/:
                            if (this.m == 'a') {
                                int[] iArr17 = this.u;
                                int i30 = this.p;
                                this.p = i30 + 1;
                                iArr17[i30] = 6;
                                continue;
                            } else {
                                continue;
                            }
                        case R.styleable.com_cauly_android_ad_AdView_allowcall /*{ENCODED_INT: 8}*/:
                            if (this.m == 'f') {
                                int[] iArr18 = this.u;
                                int i31 = this.p;
                                this.p = i31 + 1;
                                iArr18[i31] = 7;
                                continue;
                            } else {
                                continue;
                            }
                        case 9:
                            if ((576460608556236766L & j3) == 0) {
                                continue;
                            } else {
                                if (i24 > 11) {
                                    i24 = 11;
                                }
                                d(10);
                                continue;
                            }
                        case 10:
                            if ((576460745995190270L & j3) == 0) {
                                continue;
                            } else {
                                if (i24 > 11) {
                                    i24 = 11;
                                }
                                d(10);
                                continue;
                            }
                        case 11:
                            if (this.m == 'u') {
                                int[] iArr19 = this.u;
                                int i32 = this.p;
                                this.p = i32 + 1;
                                iArr19[i32] = 12;
                                continue;
                            } else {
                                continue;
                            }
                        case Defines.IAP_PACKET_HEADER_SIZE:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr20 = this.u;
                                int i33 = this.p;
                                this.p = i33 + 1;
                                iArr20[i33] = 13;
                                continue;
                            } else {
                                continue;
                            }
                        case 13:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr21 = this.u;
                                int i34 = this.p;
                                this.p = i34 + 1;
                                iArr21[i34] = 14;
                                continue;
                            } else {
                                continue;
                            }
                        case 14:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr22 = this.u;
                                int i35 = this.p;
                                this.p = i35 + 1;
                                iArr22[i35] = 15;
                                continue;
                            } else {
                                continue;
                            }
                        case 15:
                            if ((541165879422L & j3) != 0 && i24 > 14) {
                                i24 = 14;
                                continue;
                            }
                        case 16:
                            if (this.m == '\\') {
                                int[] iArr23 = this.u;
                                int i36 = this.p;
                                this.p = i36 + 1;
                                iArr23[i36] = 11;
                                continue;
                            } else {
                                continue;
                            }
                        case 18:
                            if ((-268435457 & j3) != 0) {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 19:
                            if (this.m == '\\') {
                                int[] iArr24 = this.u;
                                int i37 = this.p;
                                this.p = i37 + 1;
                                iArr24[i37] = 20;
                                continue;
                            } else {
                                continue;
                            }
                        case 20:
                            if ((5700160604602368L & j3) != 0) {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 21:
                            if (this.m == 'u') {
                                int[] iArr25 = this.u;
                                int i38 = this.p;
                                this.p = i38 + 1;
                                iArr25[i38] = 22;
                                continue;
                            } else {
                                continue;
                            }
                        case 22:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr26 = this.u;
                                int i39 = this.p;
                                this.p = i39 + 1;
                                iArr26[i39] = 23;
                                continue;
                            } else {
                                continue;
                            }
                        case 23:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr27 = this.u;
                                int i40 = this.p;
                                this.p = i40 + 1;
                                iArr27[i40] = 24;
                                continue;
                            } else {
                                continue;
                            }
                        case 24:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr28 = this.u;
                                int i41 = this.p;
                                this.p = i41 + 1;
                                iArr28[i41] = 25;
                                continue;
                            } else {
                                continue;
                            }
                        case 25:
                            if ((541165879422L & j3) != 0) {
                                c(4, 7);
                                continue;
                            } else {
                                continue;
                            }
                        case 26:
                            if (this.m == '\\') {
                                int[] iArr29 = this.u;
                                int i42 = this.p;
                                this.p = i42 + 1;
                                iArr29[i42] = 21;
                                continue;
                            } else {
                                continue;
                            }
                        case 29:
                            if ((-268435457 & j3) != 0) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 30:
                            if (this.m == '\\') {
                                int[] iArr30 = this.u;
                                int i43 = this.p;
                                this.p = i43 + 1;
                                iArr30[i43] = 31;
                                continue;
                            } else {
                                continue;
                            }
                        case 31:
                            if ((5700160604602368L & j3) != 0) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 32:
                            if (this.m == 'u') {
                                int[] iArr31 = this.u;
                                int i44 = this.p;
                                this.p = i44 + 1;
                                iArr31[i44] = 33;
                                continue;
                            } else {
                                continue;
                            }
                        case 33:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr32 = this.u;
                                int i45 = this.p;
                                this.p = i45 + 1;
                                iArr32[i45] = 34;
                                continue;
                            } else {
                                continue;
                            }
                        case 34:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr33 = this.u;
                                int i46 = this.p;
                                this.p = i46 + 1;
                                iArr33[i46] = 35;
                                continue;
                            } else {
                                continue;
                            }
                        case 35:
                            if ((541165879422L & j3) != 0) {
                                int[] iArr34 = this.u;
                                int i47 = this.p;
                                this.p = i47 + 1;
                                iArr34[i47] = 36;
                                continue;
                            } else {
                                continue;
                            }
                        case 36:
                            if ((541165879422L & j3) != 0) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                        case 37:
                            if (this.m == '\\') {
                                int[] iArr35 = this.u;
                                int i48 = this.p;
                                this.p = i48 + 1;
                                iArr35[i48] = 32;
                                continue;
                            } else {
                                continue;
                            }
                        case 39:
                            if ((137438953504L & j3) == 0) {
                                continue;
                            } else {
                                if (i24 > 12) {
                                    i24 = 12;
                                }
                                d();
                                continue;
                            }
                        case 42:
                            if ((576460745995190270L & j3) == 0) {
                                continue;
                            } else {
                                if (i24 > 12) {
                                    i24 = 12;
                                }
                                d(42);
                                continue;
                            }
                        case 43:
                            if ((-268435457 & j3) != 0) {
                                c(0, 3);
                            } else if (this.m == '\\') {
                                int[] iArr36 = this.u;
                                int i49 = this.p;
                                this.p = i49 + 1;
                                iArr36[i49] = 32;
                            }
                            if (this.m == '\\') {
                                int[] iArr37 = this.u;
                                int i50 = this.p;
                                this.p = i50 + 1;
                                iArr37[i50] = 31;
                                continue;
                            } else {
                                continue;
                            }
                    }
                } while (i23 != i6);
                i4 = i24;
            } else {
                int i51 = this.m >> '\b';
                int i52 = i51 >> 6;
                long j4 = 1 << (i51 & 63);
                int i53 = (this.m & 255) >> 6;
                long j5 = 1 << (this.m & '?');
                do {
                    i5--;
                    switch (this.u[i5]) {
                        case 18:
                            if (a(i51, i52, i53, j4, j5)) {
                                int i54 = 4;
                                while (true) {
                                    int[] iArr38 = this.u;
                                    int i55 = this.p;
                                    this.p = i55 + 1;
                                    iArr38[i55] = d[i54];
                                    int i56 = i54 + 1;
                                    if (i54 != 7) {
                                        i54 = i56;
                                    } else {
                                        continue;
                                    }
                                }
                            } else {
                                continue;
                            }
                        case 29:
                        case 43:
                            if (a(i51, i52, i53, j4, j5)) {
                                c(0, 3);
                                continue;
                            } else {
                                continue;
                            }
                    }
                } while (i5 != i6);
                i4 = i8;
            }
            if (i4 != Integer.MAX_VALUE) {
                this.s = i4;
                this.r = i7;
                i4 = Integer.MAX_VALUE;
            }
            int i57 = i7 + 1;
            int i58 = this.p;
            this.p = i6;
            int i59 = 43 - i6;
            if (i58 == i59) {
                return i57;
            }
            try {
                this.m = this.l.b();
                i5 = i58;
                i6 = i59;
                i7 = i57;
                i8 = i4;
            } catch (IOException e) {
                return i57;
            }
        }
    }

    private final int b(int i2, long j2) {
        return b(a(i2, j2), i2 + 1);
    }

    private int b(long j2) {
        long j3 = 512 & j2;
        if (j3 == 0) {
            return b(3, j2);
        }
        try {
            this.m = this.l.b();
            switch (this.m) {
                case Defines.DIALOG_STATE.DLG_PURCHASE_CONFIRM:
                    return c(j3);
                default:
                    return b(4, j3);
            }
        } catch (IOException e) {
            a(4, j3);
            return 5;
        }
    }

    /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
    private int b(long j2, long j3) {
        long j4 = j3 & j2;
        if (j4 == 0) {
            return b(1, j2);
        }
        try {
            this.m = this.l.b();
            switch (this.m) {
                case '\'':
                    return c(j4, 67108864);
                case Defines.DIALOG_STATE.DLG_PURCHASE_CONFIRM:
                    return c(j4, 512);
                case 'l':
                    if ((128 & j4) != 0) {
                        return a(3, 7, 10);
                    }
                    break;
            }
            return b(2, j4);
        } catch (IOException e) {
            a(2, j4);
            return 3;
        }
    }

    private void b() {
        this.q = -2147483647;
        int i2 = 43;
        while (true) {
            int i3 = i2 - 1;
            if (i2 > 0) {
                this.t[i3] = Integer.MIN_VALUE;
                i2 = i3;
            } else {
                return;
            }
        }
    }

    private int c(int i2) {
        int i3;
        this.p = 2;
        this.u[0] = 0;
        int i4 = Integer.MAX_VALUE;
        int i5 = 1;
        int i6 = 0;
        int i7 = i2;
        while (true) {
            int i8 = this.q + 1;
            this.q = i8;
            if (i8 == Integer.MAX_VALUE) {
                b();
            }
            if (this.m < '@') {
                long j2 = 1 << this.m;
                int i9 = i4;
                int i10 = i5;
                do {
                    i10--;
                    switch (this.u[i10]) {
                        case 0:
                            if ((-17179869185L & j2) != 0) {
                                if (i9 > 21) {
                                    i9 = 21;
                                    continue;
                                } else {
                                    continue;
                                }
                            } else if (this.m == '\"' && i9 > 20) {
                                i9 = 20;
                                continue;
                            }
                        case 1:
                            if ((-17179869185L & j2) != 0) {
                                i9 = 21;
                                continue;
                            } else {
                                continue;
                            }
                    }
                } while (i10 != i6);
                i3 = i9;
            } else if (this.m < 128) {
                long j3 = 1 << (this.m & '?');
                int i11 = i4;
                int i12 = i5;
                do {
                    i12--;
                    switch (this.u[i12]) {
                        case 0:
                            if ((-268435457 & j3) != 0) {
                                i11 = 21;
                                continue;
                            } else {
                                continue;
                            }
                    }
                } while (i12 != i6);
                i3 = i11;
            } else {
                int i13 = this.m >> '\b';
                int i14 = i13 >> 6;
                long j4 = 1 << (i13 & 63);
                int i15 = (this.m & 255) >> 6;
                long j5 = 1 << (this.m & '?');
                do {
                    i5--;
                    switch (this.u[i5]) {
                        case 0:
                            if (a(i13, i14, i15, j4, j5) && i4 > 21) {
                                i4 = 21;
                                continue;
                            }
                    }
                } while (i5 != i6);
                i3 = i4;
            }
            if (i3 != Integer.MAX_VALUE) {
                this.s = i3;
                this.r = i7;
                i3 = Integer.MAX_VALUE;
            }
            int i16 = i7 + 1;
            int i17 = this.p;
            this.p = i6;
            int i18 = 2 - i6;
            if (i17 == i18) {
                return i16;
            }
            try {
                this.m = this.l.b();
                i4 = i3;
                i5 = i17;
                i6 = i18;
                i7 = i16;
            } catch (IOException e) {
                return i16;
            }
        }
    }

    private int c(long j2) {
        long j3 = 512 & j2;
        if (j3 == 0) {
            return b(4, j2);
        }
        try {
            this.m = this.l.b();
            switch (this.m) {
                case 't':
                    return d(j3);
                default:
                    return b(5, j3);
            }
        } catch (IOException e) {
            a(5, j3);
            return 6;
        }
    }

    private int c(long j2, long j3) {
        long j4 = j3 & j2;
        if (j4 == 0) {
            return b(2, j2);
        }
        try {
            this.m = this.l.b();
            switch (this.m) {
                case 'n':
                    return b(j4);
                case '\n':
                    if ((67108864 & j4) != 0) {
                        return a(4, 26);
                    }
                    break;
            }
            return b(3, j4);
        } catch (IOException e) {
            a(3, j4);
            return 4;
        }
    }

    private az c() {
        String str = f[this.s];
        if (str == null) {
            str = this.l.g();
        }
        int f2 = this.l.f();
        int e = this.l.e();
        int d2 = this.l.d();
        int c2 = this.l.c();
        az a2 = az.a(this.s, str);
        a2.b = f2;
        a2.d = d2;
        a2.c = e;
        a2.e = c2;
        return a2;
    }

    private void c(int i2, int i3) {
        int i4 = i2;
        while (true) {
            d(d[i4]);
            int i5 = i4 + 1;
            if (i4 != i3) {
                i4 = i5;
            } else {
                return;
            }
        }
    }

    private int d(long j2) {
        long j3 = 512 & j2;
        if (j3 == 0) {
            return b(5, j2);
        }
        try {
            this.m = this.l.b();
            switch (this.m) {
                case 'y':
                    if ((j3 & 512) != 0) {
                        return a(7, 9, 10);
                    }
                    break;
            }
            return b(6, j3);
        } catch (IOException e) {
            a(6, j3);
            return 7;
        }
    }

    private void d() {
        d(40);
        d(42);
    }

    private void d(int i2) {
        if (this.t[i2] != this.q) {
            int[] iArr = this.u;
            int i3 = this.p;
            this.p = i3 + 1;
            iArr[i3] = i2;
            this.t[i2] = this.q;
        }
    }

    public final az a() {
        String str;
        int i2;
        int i3;
        boolean z;
        int i4 = 0;
        while (true) {
            try {
                this.m = this.l.a();
                while (true) {
                    switch (this.n) {
                        case 0:
                            try {
                                this.l.a(0);
                                while (this.m <= ' ' && (4294977024L & (1 << this.m)) != 0) {
                                    this.m = this.l.a();
                                }
                                this.s = Integer.MAX_VALUE;
                                this.r = 0;
                                switch (this.m) {
                                    case '\"':
                                        i4 = a(0, 18, 43);
                                        break;
                                    case ')':
                                        i4 = a(67108864L);
                                        break;
                                    case ',':
                                        i4 = a(0, 28);
                                        break;
                                    case '-':
                                        i4 = a(0, 33);
                                        break;
                                    case '.':
                                        i4 = a(0, 34);
                                        break;
                                    case ':':
                                        i4 = a(0, 30);
                                        break;
                                    case 'I':
                                        i4 = a(512L);
                                        break;
                                    case 'N':
                                        i4 = a(256L);
                                        break;
                                    case '[':
                                        i4 = a(0, 31);
                                        break;
                                    case ']':
                                        i4 = a(0, 32);
                                        break;
                                    case 'n':
                                        i4 = a(128L);
                                        break;
                                    case '{':
                                        i4 = a(0, 27);
                                        break;
                                    case '}':
                                        i4 = a(0, 29);
                                        break;
                                    default:
                                        i4 = b(4, 0);
                                        break;
                                }
                            } catch (IOException e) {
                                break;
                            }
                        case 1:
                            this.s = Integer.MAX_VALUE;
                            this.r = 0;
                            switch (this.m) {
                                case '\\':
                                    i4 = a(0, 19);
                                    break;
                                default:
                                    i4 = c(0);
                                    break;
                            }
                        case 2:
                            this.s = Integer.MAX_VALUE;
                            this.r = 0;
                            switch (this.m) {
                                case 'u':
                                    i4 = a(0, 23);
                                    break;
                                default:
                                    i4 = b(0);
                                    break;
                            }
                        case 3:
                            this.s = Integer.MAX_VALUE;
                            this.r = 0;
                            i4 = a(0);
                            break;
                    }
                    if (this.s != Integer.MAX_VALUE) {
                        if (this.r + 1 < i4) {
                            this.l.a((i4 - this.r) - 1);
                        }
                        if ((i[this.s >> 6] & (1 << (this.s & 63))) != 0) {
                            az c2 = c();
                            if (h[this.s] == -1) {
                                return c2;
                            }
                            this.n = h[this.s];
                            return c2;
                        } else if ((j[this.s >> 6] & (1 << (this.s & 63))) == 0) {
                            if (h[this.s] != -1) {
                                this.n = h[this.s];
                            }
                            this.s = Integer.MAX_VALUE;
                            try {
                                this.m = this.l.b();
                                i4 = 0;
                            } catch (IOException e2) {
                                i4 = 0;
                            }
                        } else if (h[this.s] != -1) {
                            this.n = h[this.s];
                        }
                    }
                }
            } catch (IOException e3) {
                this.s = 0;
                return c();
            }
        }
        int d2 = this.l.d();
        int c3 = this.l.c();
        try {
            this.l.b();
            this.l.a(1);
            str = null;
            i3 = c3;
            i2 = d2;
            z = false;
        } catch (IOException e4) {
            String g2 = i4 <= 1 ? "" : this.l.g();
            if (this.m == '\n' || this.m == '\r') {
                str = g2;
                i2 = d2 + 1;
                i3 = 0;
                z = true;
            } else {
                str = g2;
                i3 = c3 + 1;
                i2 = d2;
                z = true;
            }
        }
        if (!z) {
            this.l.a(1);
            str = i4 <= 1 ? "" : this.l.g();
        }
        int i5 = this.n;
        throw new ba(z, i2, i3, str, this.m);
    }
}
