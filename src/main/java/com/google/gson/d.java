package com.google.gson;

/* access modifiers changed from: package-private */
public final class d extends RuntimeException {
    private final Object a;

    d(Object obj) {
        super("circular reference error");
        this.a = obj;
    }

    public final IllegalStateException a(FieldAttributes fieldAttributes) {
        StringBuilder sb = new StringBuilder(getMessage());
        if (fieldAttributes != null) {
            sb.append("\n  ").append("Offending field: ").append(fieldAttributes.getName() + "\n");
        }
        if (this.a != null) {
            sb.append("\n  ").append("Offending object: ").append(this.a);
        }
        return new IllegalStateException(sb.toString(), this);
    }
}
