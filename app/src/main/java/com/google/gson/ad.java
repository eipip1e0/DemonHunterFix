package com.google.gson;

import com.google.gson.ObjectNavigator;
import java.lang.reflect.Array;
import java.lang.reflect.Type;

/* access modifiers changed from: package-private */
public final class ad implements ObjectNavigator.Visitor {
    private final an a;
    private final aq<JsonSerializer<?>> b;
    private final boolean c;
    private final JsonSerializationContext d;
    private final aj e;
    private JsonElement f;

    ad(an anVar, boolean z, aq<JsonSerializer<?>> aqVar, JsonSerializationContext jsonSerializationContext, aj ajVar) {
        this.a = anVar;
        this.c = z;
        this.b = aqVar;
        this.d = jsonSerializationContext;
        this.e = ajVar;
    }

    private JsonElement a(ao aoVar) {
        ObjectNavigator a2 = this.a.a(aoVar);
        ad adVar = new ad(this.a, this.c, this.b, this.d, this.e);
        a2.a(adVar);
        return adVar.f;
    }

    private void a(FieldAttributes fieldAttributes, JsonElement jsonElement) {
        this.f.getAsJsonObject().add(this.a.a().a(fieldAttributes), jsonElement);
    }

    private void a(FieldAttributes fieldAttributes, ao aoVar) {
        a(fieldAttributes, a(aoVar));
    }

    private void a(JsonElement jsonElement) {
        at.a(jsonElement);
        this.f = jsonElement;
    }

    private static boolean a(FieldAttributes fieldAttributes, Object obj) {
        return b(fieldAttributes, obj) == null;
    }

    private JsonElement b(ao aoVar) {
        ap a2 = aoVar.a((aq) this.b);
        if (a2 == null) {
            return null;
        }
        FIRST first = a2.a;
        SECOND second = a2.b;
        start(second);
        try {
            JsonElement serialize = first.serialize(second.a(), second.a, this.d);
            if (serialize == null) {
                serialize = JsonNull.b();
            }
            return serialize;
        } finally {
            end(second);
        }
    }

    private static Object b(FieldAttributes fieldAttributes, Object obj) {
        try {
            return fieldAttributes.a(obj);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException(e2);
        }
    }

    public final JsonElement a() {
        return this.f;
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void end(ao node) {
        if (node != null) {
            this.e.a();
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final Object getTarget() {
        return null;
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void start(ao node) {
        if (node != null) {
            if (this.e.b(node)) {
                throw new d(node);
            }
            this.e.a(node);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void startVisitingObject(Object obj) {
        a(new JsonObject());
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitArray(Object array, Type arrayType) {
        a(new JsonArray());
        int length = Array.getLength(array);
        Type e2 = be.a(arrayType).e();
        for (int i = 0; i < length; i++) {
            ao aoVar = new ao(Array.get(array, i), e2, false);
            if (aoVar.a() == null) {
                this.f.getAsJsonArray().add(JsonNull.b());
            } else {
                this.f.getAsJsonArray().add(a(aoVar));
            }
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitArrayField(FieldAttributes f2, Type typeOfF, Object obj) {
        try {
            if (!a(f2, obj)) {
                a(f2, new ao(b(f2, obj), typeOfF, false));
            } else if (this.c) {
                a(f2, (JsonElement) JsonNull.b());
            }
        } catch (d e2) {
            throw e2.a(f2);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final boolean visitFieldUsingCustomHandler(FieldAttributes f2, Type declaredTypeOfField, Object parent) {
        try {
            boolean isJsonObject = this.f.isJsonObject();
            if (!isJsonObject) {
                throw new IllegalArgumentException("condition failed: " + isJsonObject);
            }
            Object a2 = f2.a(parent);
            if (a2 == null) {
                if (this.c) {
                    a(f2, (JsonElement) JsonNull.b());
                }
                return true;
            }
            JsonElement b2 = b(new ao(a2, declaredTypeOfField, false));
            if (b2 == null) {
                return false;
            }
            a(f2, b2);
            return true;
        } catch (IllegalAccessException e2) {
            throw new RuntimeException();
        } catch (d e3) {
            throw e3.a(f2);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitObjectField(FieldAttributes f2, Type typeOfF, Object obj) {
        try {
            if (!a(f2, obj)) {
                a(f2, new ao(b(f2, obj), typeOfF, false));
            } else if (this.c) {
                a(f2, (JsonElement) JsonNull.b());
            }
        } catch (d e2) {
            throw e2.a(f2);
        }
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final void visitPrimitive(Object obj) {
        a(obj == null ? JsonNull.b() : new JsonPrimitive(obj));
    }

    @Override // com.google.gson.ObjectNavigator.Visitor
    public final boolean visitUsingCustomHandler(ao objTypePair) {
        try {
            if (objTypePair.a() == null) {
                if (this.c) {
                    a(JsonNull.b());
                }
                return true;
            }
            JsonElement b2 = b(objTypePair);
            if (b2 == null) {
                return false;
            }
            a(b2);
            return true;
        } catch (d e2) {
            throw e2.a(null);
        }
    }
}
