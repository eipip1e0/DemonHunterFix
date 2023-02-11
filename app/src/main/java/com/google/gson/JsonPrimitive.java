package com.google.gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

public final class JsonPrimitive extends JsonElement {
    private static final Class<?>[] a = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private static final BigInteger b = BigInteger.valueOf(2147483647L);
    private static final BigInteger c = BigInteger.valueOf(Long.MAX_VALUE);
    private Object d;

    public JsonPrimitive(Boolean bool) {
        a(bool);
    }

    public JsonPrimitive(Character c2) {
        a(c2);
    }

    public JsonPrimitive(Number number) {
        a(number);
    }

    JsonPrimitive(Object primitive) {
        a(primitive);
    }

    public JsonPrimitive(String string) {
        a(string);
    }

    private void a(Object obj) {
        boolean z;
        boolean z2;
        if (obj instanceof Character) {
            this.d = String.valueOf(((Character) obj).charValue());
            return;
        }
        if (!(obj instanceof Number)) {
            if (!(obj instanceof String)) {
                Class<?> cls = obj.getClass();
                Class<?>[] clsArr = a;
                int length = clsArr.length;
                int i = 0;
                while (true) {
                    if (i >= length) {
                        z2 = false;
                        break;
                    } else if (clsArr[i].isAssignableFrom(cls)) {
                        z2 = true;
                        break;
                    } else {
                        i++;
                    }
                }
            } else {
                z2 = true;
            }
            if (!z2) {
                z = false;
                at.a(z);
                this.d = obj;
            }
        }
        z = true;
        at.a(z);
        this.d = obj;
    }

    private static boolean a(JsonPrimitive jsonPrimitive) {
        if (!(jsonPrimitive.d instanceof Number)) {
            return false;
        }
        Number number = (Number) jsonPrimitive.d;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }

    private static boolean b(JsonPrimitive jsonPrimitive) {
        if (!(jsonPrimitive.d instanceof Number)) {
            return false;
        }
        Number number = (Number) jsonPrimitive.d;
        return (number instanceof BigDecimal) || (number instanceof Double) || (number instanceof Float);
    }

    /* access modifiers changed from: package-private */
    @Override // com.google.gson.JsonElement
    public final Object a() {
        if (this.d instanceof BigInteger) {
            BigInteger bigInteger = (BigInteger) this.d;
            if (bigInteger.compareTo(b) < 0) {
                return Integer.valueOf(bigInteger.intValue());
            }
            if (bigInteger.compareTo(c) < 0) {
                return Long.valueOf(bigInteger.longValue());
            }
        }
        return this.d;
    }

    /* access modifiers changed from: protected */
    @Override // com.google.gson.JsonElement
    public final void a(Appendable appendable, h hVar) throws IOException {
        if (isString()) {
            appendable.append('\"');
            appendable.append(hVar.a(this.d.toString()));
            appendable.append('\"');
            return;
        }
        appendable.append(this.d.toString());
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        JsonPrimitive jsonPrimitive = (JsonPrimitive) obj;
        if (this.d == null) {
            return jsonPrimitive.d == null;
        }
        if (a(this) && a(jsonPrimitive)) {
            return getAsNumber().longValue() == jsonPrimitive.getAsNumber().longValue();
        }
        if (!b(this) || !b(jsonPrimitive)) {
            return this.d.equals(jsonPrimitive.d);
        }
        return getAsNumber().doubleValue() == jsonPrimitive.getAsNumber().doubleValue();
    }

    @Override // com.google.gson.JsonElement
    public final BigDecimal getAsBigDecimal() {
        return this.d instanceof BigDecimal ? (BigDecimal) this.d : new BigDecimal(this.d.toString());
    }

    @Override // com.google.gson.JsonElement
    public final BigInteger getAsBigInteger() {
        return this.d instanceof BigInteger ? (BigInteger) this.d : new BigInteger(this.d.toString());
    }

    @Override // com.google.gson.JsonElement
    public final boolean getAsBoolean() {
        return isBoolean() ? ((Boolean) this.d).booleanValue() : Boolean.parseBoolean(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final byte getAsByte() {
        return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final char getAsCharacter() {
        return getAsString().charAt(0);
    }

    @Override // com.google.gson.JsonElement
    public final double getAsDouble() {
        return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final float getAsFloat() {
        return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final int getAsInt() {
        return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final long getAsLong() {
        return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final Number getAsNumber() {
        return (Number) this.d;
    }

    @Override // com.google.gson.JsonElement
    public final short getAsShort() {
        return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public final String getAsString() {
        return isNumber() ? getAsNumber().toString() : isBoolean() ? ((Boolean) this.d).toString() : (String) this.d;
    }

    public final int hashCode() {
        if (this.d == null) {
            return 31;
        }
        if (a(this)) {
            long longValue = getAsNumber().longValue();
            return (int) (longValue ^ (longValue >>> 32));
        } else if (!b(this)) {
            return this.d.hashCode();
        } else {
            long doubleToLongBits = Double.doubleToLongBits(getAsNumber().doubleValue());
            return (int) (doubleToLongBits ^ (doubleToLongBits >>> 32));
        }
    }

    public final boolean isBoolean() {
        return this.d instanceof Boolean;
    }

    public final boolean isNumber() {
        return this.d instanceof Number;
    }

    public final boolean isString() {
        return this.d instanceof String;
    }
}
