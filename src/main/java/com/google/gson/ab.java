package com.google.gson;

import java.io.IOException;

/* access modifiers changed from: package-private */
public final class ab implements w {
    private final int a;
    private final int b;
    private final int c;
    private final boolean d;

    /* access modifiers changed from: private */
    public class a {
        private final Appendable b;
        private StringBuilder c = new StringBuilder();
        private int d = 0;

        a(Appendable appendable) {
            this.b = appendable;
        }

        private void a(int i) throws IOException {
            if (h().length() + i > ab.this.a - ab.this.c) {
                g();
            }
        }

        /* access modifiers changed from: private */
        /* access modifiers changed from: public */
        private void g() throws IOException {
            if (this.c != null) {
                this.b.append(this.c).append("\n");
            }
            this.c = null;
        }

        private StringBuilder h() {
            if (this.c == null) {
                this.c = new StringBuilder();
                for (int i = 0; i < this.d; i++) {
                    for (int i2 = 0; i2 < ab.this.b; i2++) {
                        this.c.append(' ');
                    }
                }
            }
            return this.c;
        }

        /* access modifiers changed from: package-private */
        public final void a() throws IOException {
            h().append(':');
            a(0);
        }

        /* access modifiers changed from: package-private */
        public final void a(String str) throws IOException {
            a(str.length() + 2);
            h().append('\"');
            h().append(str);
            h().append('\"');
        }

        /* access modifiers changed from: package-private */
        public final void b() throws IOException {
            h().append(',');
            a(0);
        }

        /* access modifiers changed from: package-private */
        public final void b(String str) throws IOException {
            a(str.length() + 2);
            h().append(str);
        }

        /* access modifiers changed from: package-private */
        public final void c() throws IOException {
            a(0);
            h().append('{');
            this.d++;
        }

        /* access modifiers changed from: package-private */
        public final void d() {
            h().append('}');
            this.d--;
        }

        /* access modifiers changed from: package-private */
        public final void e() throws IOException {
            a(0);
            h().append('[');
            this.d++;
        }

        /* access modifiers changed from: package-private */
        public final void f() {
            h().append(']');
            this.d--;
        }
    }

    private class b implements u {
        private final a b;
        private final h c;
        private final boolean d;

        b(a aVar, h hVar, boolean z) {
            this.b = aVar;
            this.c = hVar;
            this.d = z;
        }

        private String b(JsonPrimitive jsonPrimitive) throws IOException {
            StringBuilder sb = new StringBuilder();
            jsonPrimitive.a(sb, this.c);
            return sb.toString();
        }

        private void d(boolean z) throws IOException {
            if (!z) {
                this.b.b();
            }
        }

        @Override // com.google.gson.u
        public final void a() throws IOException {
            this.b.b("null");
        }

        @Override // com.google.gson.u
        public final void a(JsonPrimitive jsonPrimitive) throws IOException {
            this.b.b(b(jsonPrimitive));
        }

        @Override // com.google.gson.u
        public final void a(JsonPrimitive jsonPrimitive, boolean z) throws IOException {
            d(z);
            this.b.b(b(jsonPrimitive));
        }

        @Override // com.google.gson.u
        public final void a(String str, JsonPrimitive jsonPrimitive, boolean z) throws IOException {
            d(z);
            this.b.a(str);
            this.b.a();
            this.b.b(b(jsonPrimitive));
        }

        @Override // com.google.gson.u
        public final void a(String str, boolean z) throws IOException {
            d(z);
            this.b.a(str);
            this.b.a();
        }

        @Override // com.google.gson.u
        public final void a(boolean z) throws IOException {
            d(z);
        }

        @Override // com.google.gson.u
        public final void b() throws IOException {
            this.b.e();
        }

        @Override // com.google.gson.u
        public final void b(String str, boolean z) throws IOException {
            d(z);
            this.b.a(str);
            this.b.a();
        }

        @Override // com.google.gson.u
        public final void b(boolean z) throws IOException {
            d(z);
        }

        @Override // com.google.gson.u
        public final void c() {
            this.b.f();
        }

        @Override // com.google.gson.u
        public final void c(String str, boolean z) throws IOException {
            if (this.d) {
                b(str, z);
            }
        }

        @Override // com.google.gson.u
        public final void c(boolean z) throws IOException {
            d(z);
        }

        @Override // com.google.gson.u
        public final void d() throws IOException {
            this.b.c();
        }

        @Override // com.google.gson.u
        public final void e() {
            this.b.d();
        }
    }

    ab() {
        this(true, (byte) 0);
    }

    private ab(boolean z) {
        this.a = 80;
        this.b = 2;
        this.c = 4;
        this.d = z;
    }

    ab(boolean z, byte b2) {
        this(z);
    }

    @Override // com.google.gson.w
    public final void a(JsonElement jsonElement, Appendable appendable, boolean z) throws IOException {
        if (jsonElement != null) {
            a aVar = new a(appendable);
            new ae(new b(aVar, new h(this.d), z), z).a(jsonElement);
            aVar.g();
        }
    }
}
