package defpackage;

import java.io.UnsupportedEncodingException;

/* renamed from: v  reason: default package */
public class v {
    static final /* synthetic */ boolean a = (!v.class.desiredAssertionStatus());

    /* renamed from: v$a */
    public static abstract class a {
        public byte[] a;
        public int b;
    }

    /* renamed from: v$b */
    public static class b extends a {
        static final /* synthetic */ boolean g = (!v.class.desiredAssertionStatus());
        private static final byte[] h = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
        private static final byte[] i = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95};
        public int c = 0;
        public final boolean d = false;
        public final boolean e = false;
        public final boolean f = false;
        private final byte[] j = new byte[2];
        private int k;
        private final byte[] l = i;

        public b() {
            this.a = null;
            this.k = this.e ? 19 : -1;
        }

        /* JADX INFO: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x005e  */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x0109  */
        /* JADX WARNING: Removed duplicated region for block: B:47:0x0174  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final boolean a(byte[] r12, int r13) {
            /*
            // Method dump skipped, instructions count: 578
            */
            throw new UnsupportedOperationException("Method not decompiled: defpackage.v.b.a(byte[], int):boolean");
        }
    }

    private v() {
    }

    public static String a(byte[] bArr) {
        try {
            int length = bArr.length;
            b bVar = new b();
            int i = (length / 3) * 4;
            if (!bVar.d) {
                switch (length % 3) {
                    case 1:
                        i += 2;
                        break;
                    case 2:
                        i += 3;
                        break;
                }
            } else if (length % 3 > 0) {
                i += 4;
            }
            if (bVar.e && length > 0) {
                i += (((length - 1) / 57) + 1) * (bVar.f ? 2 : 1);
            }
            bVar.a = new byte[i];
            bVar.a(bArr, length);
            if (a || bVar.b == i) {
                return new String(bVar.a, "US-ASCII");
            }
            throw new AssertionError();
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }
}
