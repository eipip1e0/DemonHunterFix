package com.google.gson;

import java.io.IOException;

final class q implements w {
    private final boolean a;

    private static class a implements u {
        private final Appendable a;
        private final h b;
        private final boolean c;

        a(Appendable appendable, h hVar, boolean z) {
            this.a = appendable;
            this.b = hVar;
            this.c = z;
        }

        @Override // com.google.gson.u
        public final void a() throws IOException {
            this.a.append("null");
        }

        @Override // com.google.gson.u
        public final void a(JsonPrimitive jsonPrimitive) throws IOException {
            jsonPrimitive.a(this.a, this.b);
        }

        @Override // com.google.gson.u
        public final void a(JsonPrimitive jsonPrimitive, boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
            jsonPrimitive.a(this.a, this.b);
        }

        @Override // com.google.gson.u
        public final void a(String str, JsonPrimitive jsonPrimitive, boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
            this.a.append('\"');
            this.a.append(str);
            this.a.append("\":");
            jsonPrimitive.a(this.a, this.b);
        }

        @Override // com.google.gson.u
        public final void a(String str, boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
            this.a.append('\"');
            this.a.append(str);
            this.a.append("\":");
        }

        @Override // com.google.gson.u
        public final void a(boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
        }

        @Override // com.google.gson.u
        public final void b() throws IOException {
            this.a.append('[');
        }

        @Override // com.google.gson.u
        public final void b(String str, boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
            this.a.append('\"');
            this.a.append(str);
            this.a.append("\":");
        }

        @Override // com.google.gson.u
        public final void b(boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
        }

        @Override // com.google.gson.u
        public final void c() throws IOException {
            this.a.append(']');
        }

        @Override // com.google.gson.u
        public final void c(String str, boolean z) throws IOException {
            if (this.c) {
                b(str, z);
            }
        }

        @Override // com.google.gson.u
        public final void c(boolean z) throws IOException {
            if (!z) {
                this.a.append(',');
            }
        }

        @Override // com.google.gson.u
        public final void d() throws IOException {
            this.a.append('{');
        }

        @Override // com.google.gson.u
        public final void e() throws IOException {
            this.a.append('}');
        }
    }

    q() {
        this(true);
    }

    q(boolean z) {
        this.a = z;
    }

    @Override // com.google.gson.w
    public final void a(JsonElement jsonElement, Appendable appendable, boolean z) throws IOException {
        if (jsonElement != null) {
            new ae(new a(appendable, new h(this.a), z), z).a(jsonElement);
        }
    }
}
