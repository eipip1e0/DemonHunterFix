package com.google.gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.UUID;

/* access modifiers changed from: package-private */
public final class f {
    private static final p A = new p();
    private static final aq<JsonSerializer<?>> B;
    private static final aq<JsonDeserializer<?>> C;
    private static final aq<InstanceCreator<?>> D;
    private static final g a = new g();
    private static final h b = new h();
    private static final i c = new i();
    private static final j d = new j();
    private static final m e = new m();
    private static final ac f = new ac();
    private static final ab g = new ab();
    private static final ad h = new ad();
    private static final s i = new s();
    private static final C0001f j = new C0001f();
    private static final v k = new v();
    private static final a l = new a();
    private static final b m = new b();
    private static final c n = new c();
    private static final d o = new d();
    private static final e p = new e();
    private static final k q = new k();
    private static final n r = new n();
    private static final r s = new r();
    private static final t t = new t();
    private static final w u = new w();
    private static final y v = new y();
    private static final z w = new z();
    private static final x x = new x();
    private static final aa y = new aa();
    private static final q z = new q();

    private static class a implements JsonSerializer<BigDecimal>, JsonDeserializer<BigDecimal> {
        /* synthetic */ a() {
            this((byte) 0);
        }

        private a(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ BigDecimal deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return x0.getAsBigDecimal();
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(BigDecimal bigDecimal, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive((Number) bigDecimal);
        }

        public String toString() {
            return a.class.getSimpleName();
        }
    }

    private static class aa implements InstanceCreator<TreeSet<?>> {
        /* synthetic */ aa() {
            this((byte) 0);
        }

        private aa(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.InstanceCreator
        public /* synthetic */ TreeSet<?> createInstance(Type type) {
            return new TreeSet();
        }

        public String toString() {
            return aa.class.getSimpleName();
        }
    }

    private static class ab implements JsonSerializer<URI>, JsonDeserializer<URI> {
        /* synthetic */ ab() {
            this((byte) 0);
        }

        private ab(byte b) {
        }

        private static URI a(JsonElement jsonElement) throws JsonParseException {
            try {
                return new URI(jsonElement.getAsString());
            } catch (URISyntaxException e) {
                throw new JsonParseException(e);
            }
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ URI deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return a(x0);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(URI uri, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(uri.toASCIIString());
        }

        public String toString() {
            return ab.class.getSimpleName();
        }
    }

    private static class ac implements JsonSerializer<URL>, JsonDeserializer<URL> {
        /* synthetic */ ac() {
            this((byte) 0);
        }

        private ac(byte b) {
        }

        private static URL a(JsonElement jsonElement) throws JsonParseException {
            try {
                return new URL(jsonElement.getAsString());
            } catch (MalformedURLException e) {
                throw new JsonParseException(e);
            }
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ URL deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return a(x0);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(URL url, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(url.toExternalForm());
        }

        public String toString() {
            return ac.class.getSimpleName();
        }
    }

    private static class ad implements JsonSerializer<UUID>, JsonDeserializer<UUID> {
        /* synthetic */ ad() {
            this((byte) 0);
        }

        private ad(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ UUID deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return UUID.fromString(x0.getAsString());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(UUID uuid, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(uuid.toString());
        }

        public String toString() {
            return ad.class.getSimpleName();
        }
    }

    private static class b implements JsonSerializer<BigInteger>, JsonDeserializer<BigInteger> {
        /* synthetic */ b() {
            this((byte) 0);
        }

        private b(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ BigInteger deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return x0.getAsBigInteger();
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(BigInteger bigInteger, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive((Number) bigInteger);
        }

        public String toString() {
            return b.class.getSimpleName();
        }
    }

    private static class c implements JsonSerializer<Boolean>, JsonDeserializer<Boolean> {
        /* synthetic */ c() {
            this((byte) 0);
        }

        private c(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Boolean deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Boolean.valueOf(x0.getAsBoolean());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Boolean bool, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(bool);
        }

        public String toString() {
            return c.class.getSimpleName();
        }
    }

    private static class d implements JsonSerializer<Byte>, JsonDeserializer<Byte> {
        /* synthetic */ d() {
            this((byte) 0);
        }

        private d(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Byte deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Byte.valueOf(x0.getAsByte());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Byte b, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive((Number) b);
        }

        public String toString() {
            return d.class.getSimpleName();
        }
    }

    private static class e implements JsonSerializer<Character>, JsonDeserializer<Character> {
        /* synthetic */ e() {
            this((byte) 0);
        }

        private e(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Character deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Character.valueOf(x0.getAsCharacter());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Character ch, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(ch);
        }

        public String toString() {
            return e.class.getSimpleName();
        }
    }

    /* renamed from: com.google.gson.f$f  reason: collision with other inner class name */
    private static class C0001f implements JsonSerializer<Collection>, JsonDeserializer<Collection>, InstanceCreator<Collection> {
        /* synthetic */ C0001f() {
            this((byte) 0);
        }

        private C0001f(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.InstanceCreator
        public final /* synthetic */ Collection createInstance(Type type) {
            return new LinkedList();
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public final /* synthetic */ Collection deserialize(JsonElement x0, Type x1, JsonDeserializationContext x2) throws JsonParseException {
            if (x0.isJsonNull()) {
                return null;
            }
            Collection collection = (Collection) ((r) x2).a().a(x1);
            Type a = new bd(x1).a();
            Iterator<JsonElement> it = x0.getAsJsonArray().iterator();
            while (it.hasNext()) {
                JsonElement next = it.next();
                if (next == null || next.isJsonNull()) {
                    collection.add(null);
                } else {
                    collection.add(x2.deserialize(next, a));
                }
            }
            return collection;
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public final /* synthetic */ JsonElement serialize(Collection collection, Type x1, JsonSerializationContext x2) {
            Collection collection2 = collection;
            if (collection2 == null) {
                return JsonNull.b();
            }
            JsonArray jsonArray = new JsonArray();
            Type type = null;
            if (x1 instanceof ParameterizedType) {
                type = new bd(x1).a();
            }
            for (Object obj : collection2) {
                if (obj == null) {
                    jsonArray.add(JsonNull.b());
                } else {
                    jsonArray.add(x2.serialize(obj, (type == null || type == Object.class) ? obj.getClass() : type));
                }
            }
            return jsonArray;
        }
    }

    static class g implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private final DateFormat a;

        g() {
            this.a = DateFormat.getDateTimeInstance();
        }

        public g(int i, int i2) {
            this.a = DateFormat.getDateTimeInstance(i, i2);
        }

        g(String str) {
            this.a = new SimpleDateFormat(str);
        }

        private JsonElement a(Date date) {
            JsonPrimitive jsonPrimitive;
            synchronized (this.a) {
                jsonPrimitive = new JsonPrimitive(this.a.format(date));
            }
            return jsonPrimitive;
        }

        private Date a(JsonElement jsonElement) throws JsonParseException {
            Date parse;
            if (!(jsonElement instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }
            try {
                synchronized (this.a) {
                    parse = this.a.parse(jsonElement.getAsString());
                }
                return parse;
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Date deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return a(x0);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return a(date);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(g.class.getSimpleName());
            sb.append('(').append(this.a.getClass().getSimpleName()).append(')');
            return sb.toString();
        }
    }

    static class h implements JsonSerializer<java.sql.Date>, JsonDeserializer<java.sql.Date> {
        private final DateFormat a = new SimpleDateFormat("MMM d, yyyy");

        h() {
        }

        private JsonElement a(java.sql.Date date) {
            JsonPrimitive jsonPrimitive;
            synchronized (this.a) {
                jsonPrimitive = new JsonPrimitive(this.a.format(date));
            }
            return jsonPrimitive;
        }

        private java.sql.Date a(JsonElement jsonElement) throws JsonParseException {
            java.sql.Date date;
            if (!(jsonElement instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }
            try {
                synchronized (this.a) {
                    date = new java.sql.Date(this.a.parse(jsonElement.getAsString()).getTime());
                }
                return date;
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public final /* synthetic */ java.sql.Date deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return a(x0);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public final /* synthetic */ JsonElement serialize(java.sql.Date date, Type type, JsonSerializationContext jsonSerializationContext) {
            return a(date);
        }
    }

    static class i implements JsonSerializer<Time>, JsonDeserializer<Time> {
        private final DateFormat a = new SimpleDateFormat("hh:mm:ss a");

        i() {
        }

        private JsonElement a(Time time) {
            JsonPrimitive jsonPrimitive;
            synchronized (this.a) {
                jsonPrimitive = new JsonPrimitive(this.a.format(time));
            }
            return jsonPrimitive;
        }

        private Time a(JsonElement jsonElement) throws JsonParseException {
            Time time;
            if (!(jsonElement instanceof JsonPrimitive)) {
                throw new JsonParseException("The date should be a string value");
            }
            try {
                synchronized (this.a) {
                    time = new Time(this.a.parse(jsonElement.getAsString()).getTime());
                }
                return time;
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public final /* synthetic */ Time deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return a(x0);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public final /* synthetic */ JsonElement serialize(Time time, Type type, JsonSerializationContext jsonSerializationContext) {
            return a(time);
        }
    }

    static class j implements JsonDeserializer<Timestamp> {
        j() {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public final /* synthetic */ Timestamp deserialize(JsonElement x0, Type type, JsonDeserializationContext x2) throws JsonParseException {
            return new Timestamp(((Date) x2.deserialize(x0, Date.class)).getTime());
        }
    }

    private static class k implements JsonDeserializer<Double> {
        /* synthetic */ k() {
            this((byte) 0);
        }

        private k(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Double deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Double.valueOf(x0.getAsDouble());
        }

        public String toString() {
            return k.class.getSimpleName();
        }
    }

    /* access modifiers changed from: package-private */
    public static class l implements JsonSerializer<Double> {
        private final boolean a;

        l(boolean z) {
            this.a = z;
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public final /* synthetic */ JsonElement serialize(Double d, Type type, JsonSerializationContext jsonSerializationContext) {
            Double d2 = d;
            if (this.a || (!Double.isNaN(d2.doubleValue()) && !Double.isInfinite(d2.doubleValue()))) {
                return new JsonPrimitive((Number) d2);
            }
            throw new IllegalArgumentException(d2 + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialDoubleValues() method.");
        }
    }

    private static class m<T extends Enum<T>> implements JsonSerializer<T>, JsonDeserializer<T> {
        /* synthetic */ m() {
            this((byte) 0);
        }

        private m(byte b) {
        }

        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Enum.valueOf((Class) type, jsonElement.getAsString());
        }

        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Object x0, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(((Enum) x0).name());
        }

        public String toString() {
            return m.class.getSimpleName();
        }
    }

    private static class n implements JsonDeserializer<Float> {
        /* synthetic */ n() {
            this((byte) 0);
        }

        private n(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Float deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Float.valueOf(x0.getAsFloat());
        }

        public String toString() {
            return n.class.getSimpleName();
        }
    }

    /* access modifiers changed from: package-private */
    public static class o implements JsonSerializer<Float> {
        private final boolean a;

        o(boolean z) {
            this.a = z;
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public final /* synthetic */ JsonElement serialize(Float f, Type type, JsonSerializationContext jsonSerializationContext) {
            Float f2 = f;
            if (this.a || (!Float.isNaN(f2.floatValue()) && !Float.isInfinite(f2.floatValue()))) {
                return new JsonPrimitive((Number) f2);
            }
            throw new IllegalArgumentException(f2 + " is not a valid float value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private static class p implements JsonSerializer<GregorianCalendar>, JsonDeserializer<GregorianCalendar> {
        /* synthetic */ p() {
            this((byte) 0);
        }

        private p(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ GregorianCalendar deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject asJsonObject = x0.getAsJsonObject();
            return new GregorianCalendar(asJsonObject.get("year").getAsInt(), asJsonObject.get("month").getAsInt(), asJsonObject.get("dayOfMonth").getAsInt(), asJsonObject.get("hourOfDay").getAsInt(), asJsonObject.get("minute").getAsInt(), asJsonObject.get("second").getAsInt());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(GregorianCalendar gregorianCalendar, Type type, JsonSerializationContext jsonSerializationContext) {
            GregorianCalendar gregorianCalendar2 = gregorianCalendar;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("year", Integer.valueOf(gregorianCalendar2.get(1)));
            jsonObject.addProperty("month", Integer.valueOf(gregorianCalendar2.get(2)));
            jsonObject.addProperty("dayOfMonth", Integer.valueOf(gregorianCalendar2.get(5)));
            jsonObject.addProperty("hourOfDay", Integer.valueOf(gregorianCalendar2.get(11)));
            jsonObject.addProperty("minute", Integer.valueOf(gregorianCalendar2.get(12)));
            jsonObject.addProperty("second", Integer.valueOf(gregorianCalendar2.get(13)));
            return jsonObject;
        }

        public String toString() {
            return p.class.getSimpleName();
        }
    }

    private static class q implements InstanceCreator<HashSet<?>> {
        /* synthetic */ q() {
            this((byte) 0);
        }

        private q(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.InstanceCreator
        public /* synthetic */ HashSet<?> createInstance(Type type) {
            return new HashSet();
        }

        public String toString() {
            return q.class.getSimpleName();
        }
    }

    private static class r implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
        /* synthetic */ r() {
            this((byte) 0);
        }

        private r(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Integer deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Integer.valueOf(x0.getAsInt());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Integer num, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive((Number) num);
        }

        public String toString() {
            return r.class.getSimpleName();
        }
    }

    private static class s implements JsonSerializer<Locale>, JsonDeserializer<Locale> {
        /* synthetic */ s() {
            this((byte) 0);
        }

        private s(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Locale deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            StringTokenizer stringTokenizer = new StringTokenizer(x0.getAsString(), "_");
            String nextToken = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken2 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            String nextToken3 = stringTokenizer.hasMoreElements() ? stringTokenizer.nextToken() : null;
            if (nextToken2 == null && nextToken3 == null) {
                return new Locale(nextToken);
            }
            return nextToken3 == null ? new Locale(nextToken, nextToken2) : new Locale(nextToken, nextToken2, nextToken3);
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Locale locale, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(locale.toString());
        }

        public String toString() {
            return s.class.getSimpleName();
        }
    }

    private static class t implements JsonDeserializer<Long> {
        /* synthetic */ t() {
            this((byte) 0);
        }

        private t(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Long deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Long.valueOf(x0.getAsLong());
        }

        public String toString() {
            return t.class.getSimpleName();
        }
    }

    /* access modifiers changed from: private */
    public static class u implements JsonSerializer<Long> {
        private final LongSerializationPolicy a;

        /* synthetic */ u(LongSerializationPolicy longSerializationPolicy) {
            this(longSerializationPolicy, (byte) 0);
        }

        private u(LongSerializationPolicy longSerializationPolicy, byte b) {
            this.a = longSerializationPolicy;
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* bridge */ /* synthetic */ JsonElement serialize(Long l, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.a.serialize(l);
        }

        public String toString() {
            return u.class.getSimpleName();
        }
    }

    static class v implements JsonSerializer<Map>, JsonDeserializer<Map>, InstanceCreator<Map> {
        v() {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.InstanceCreator
        public /* synthetic */ Map createInstance(Type type) {
            return new LinkedHashMap();
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Map deserialize(JsonElement x0, Type x1, JsonDeserializationContext x2) throws JsonParseException {
            Map map = (Map) ((r) x2).a().a(x1);
            bf bfVar = new bf(x1);
            for (Map.Entry<String, JsonElement> entry : x0.getAsJsonObject().entrySet()) {
                map.put(x2.deserialize(new JsonPrimitive(entry.getKey()), bfVar.a()), x2.deserialize(entry.getValue(), bfVar.b()));
            }
            return map;
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Map map, Type x1, JsonSerializationContext x2) {
            Map map2 = map;
            JsonObject jsonObject = new JsonObject();
            Type type = null;
            if (x1 instanceof ParameterizedType) {
                type = new bf(x1).b();
            }
            for (Map.Entry entry : map2.entrySet()) {
                Object value = entry.getValue();
                jsonObject.add(String.valueOf(entry.getKey()), value == null ? JsonNull.b() : x2.serialize(value, type == null ? value.getClass() : type));
            }
            return jsonObject;
        }

        public String toString() {
            return v.class.getSimpleName();
        }
    }

    private static class w implements JsonSerializer<Number>, JsonDeserializer<Number> {
        /* synthetic */ w() {
            this((byte) 0);
        }

        private w(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Number deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return x0.getAsNumber();
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Number number, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(number);
        }

        public String toString() {
            return w.class.getSimpleName();
        }
    }

    private static class x implements InstanceCreator<Properties> {
        /* synthetic */ x() {
            this((byte) 0);
        }

        private x(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.InstanceCreator
        public final /* synthetic */ Properties createInstance(Type type) {
            return new Properties();
        }
    }

    private static class y implements JsonSerializer<Short>, JsonDeserializer<Short> {
        /* synthetic */ y() {
            this((byte) 0);
        }

        private y(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ Short deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return Short.valueOf(x0.getAsShort());
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(Short sh, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive((Number) sh);
        }

        public String toString() {
            return y.class.getSimpleName();
        }
    }

    private static class z implements JsonSerializer<String>, JsonDeserializer<String> {
        /* synthetic */ z() {
            this((byte) 0);
        }

        private z(byte b) {
        }

        /* Return type fixed from 'java.lang.Object' to match base method */
        @Override // com.google.gson.JsonDeserializer
        public /* synthetic */ String deserialize(JsonElement x0, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return x0.getAsString();
        }

        /* JADX DEBUG: Method arguments types fixed to match base method, original types: [java.lang.Object, java.lang.reflect.Type, com.google.gson.JsonSerializationContext] */
        @Override // com.google.gson.JsonSerializer
        public /* synthetic */ JsonElement serialize(String str, Type type, JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(str);
        }

        public String toString() {
            return z.class.getSimpleName();
        }
    }

    static {
        aq<JsonSerializer<?>> aqVar = new aq<>();
        aqVar.a(Enum.class, (JsonSerializer<?>) e);
        aqVar.a((Type) URL.class, (JsonSerializer<?>) f);
        aqVar.a((Type) URI.class, (JsonSerializer<?>) g);
        aqVar.a((Type) UUID.class, (JsonSerializer<?>) h);
        aqVar.a((Type) Locale.class, (JsonSerializer<?>) i);
        aqVar.a(Collection.class, (JsonSerializer<?>) j);
        aqVar.a(Map.class, (JsonSerializer<?>) k);
        aqVar.a((Type) Date.class, (JsonSerializer<?>) a);
        aqVar.a((Type) java.sql.Date.class, (JsonSerializer<?>) b);
        aqVar.a((Type) Timestamp.class, (JsonSerializer<?>) a);
        aqVar.a((Type) Time.class, (JsonSerializer<?>) c);
        aqVar.a((Type) Calendar.class, (JsonSerializer<?>) A);
        aqVar.a((Type) GregorianCalendar.class, (JsonSerializer<?>) A);
        aqVar.a((Type) BigDecimal.class, (JsonSerializer<?>) l);
        aqVar.a((Type) BigInteger.class, (JsonSerializer<?>) m);
        aqVar.a((Type) Boolean.class, (JsonSerializer<?>) n);
        aqVar.a((Type) Boolean.TYPE, (JsonSerializer<?>) n);
        aqVar.a((Type) Byte.class, (JsonSerializer<?>) o);
        aqVar.a((Type) Byte.TYPE, (JsonSerializer<?>) o);
        aqVar.a((Type) Character.class, (JsonSerializer<?>) p);
        aqVar.a((Type) Character.TYPE, (JsonSerializer<?>) p);
        aqVar.a((Type) Integer.class, (JsonSerializer<?>) s);
        aqVar.a((Type) Integer.TYPE, (JsonSerializer<?>) s);
        aqVar.a((Type) Number.class, (JsonSerializer<?>) u);
        aqVar.a((Type) Short.class, (JsonSerializer<?>) v);
        aqVar.a((Type) Short.TYPE, (JsonSerializer<?>) v);
        aqVar.a((Type) String.class, (JsonSerializer<?>) w);
        aqVar.a();
        B = aqVar;
        aq<JsonDeserializer<?>> aqVar2 = new aq<>();
        aqVar2.a(Enum.class, a(e));
        aqVar2.a((Type) URL.class, a(f));
        aqVar2.a((Type) URI.class, a(g));
        aqVar2.a((Type) UUID.class, a(h));
        aqVar2.a((Type) Locale.class, a(i));
        aqVar2.a(Collection.class, a(j));
        aqVar2.a(Map.class, a(k));
        aqVar2.a((Type) Date.class, a(a));
        aqVar2.a((Type) java.sql.Date.class, a(b));
        aqVar2.a((Type) Timestamp.class, a(d));
        aqVar2.a((Type) Time.class, a(c));
        aqVar2.a((Type) Calendar.class, (JsonDeserializer<?>) A);
        aqVar2.a((Type) GregorianCalendar.class, (JsonDeserializer<?>) A);
        aqVar2.a((Type) BigDecimal.class, a(l));
        aqVar2.a((Type) BigInteger.class, a(m));
        aqVar2.a((Type) Boolean.class, a(n));
        aqVar2.a((Type) Boolean.TYPE, a(n));
        aqVar2.a((Type) Byte.class, a(o));
        aqVar2.a((Type) Byte.TYPE, a(o));
        aqVar2.a((Type) Character.class, a(p));
        aqVar2.a((Type) Character.TYPE, a(p));
        aqVar2.a((Type) Double.class, a(q));
        aqVar2.a((Type) Double.TYPE, a(q));
        aqVar2.a((Type) Float.class, a(r));
        aqVar2.a((Type) Float.TYPE, a(r));
        aqVar2.a((Type) Integer.class, a(s));
        aqVar2.a((Type) Integer.TYPE, a(s));
        aqVar2.a((Type) Long.class, a(t));
        aqVar2.a((Type) Long.TYPE, a(t));
        aqVar2.a((Type) Number.class, a(u));
        aqVar2.a((Type) Short.class, a(v));
        aqVar2.a((Type) Short.TYPE, a(v));
        aqVar2.a((Type) String.class, a(w));
        aqVar2.a();
        C = aqVar2;
        aq<InstanceCreator<?>> aqVar3 = new aq<>();
        aqVar3.a(Map.class, (InstanceCreator<?>) k);
        aqVar3.a(Collection.class, (InstanceCreator<?>) j);
        aqVar3.a(Set.class, (InstanceCreator<?>) z);
        aqVar3.a(SortedSet.class, (InstanceCreator<?>) y);
        aqVar3.a((Type) Properties.class, (InstanceCreator<?>) x);
        aqVar3.a();
        D = aqVar3;
    }

    f() {
    }

    private static JsonDeserializer<?> a(JsonDeserializer<?> jsonDeserializer) {
        return new t(jsonDeserializer);
    }

    static aq<JsonSerializer<?>> a() {
        return a(false, LongSerializationPolicy.DEFAULT);
    }

    static aq<JsonSerializer<?>> a(boolean z2, LongSerializationPolicy longSerializationPolicy) {
        aq<JsonSerializer<?>> aqVar = new aq<>();
        l lVar = new l(z2);
        aqVar.b(Double.class, lVar);
        aqVar.b(Double.TYPE, lVar);
        o oVar = new o(z2);
        aqVar.b(Float.class, oVar);
        aqVar.b(Float.TYPE, oVar);
        u uVar = new u(longSerializationPolicy);
        aqVar.b(Long.class, uVar);
        aqVar.b(Long.TYPE, uVar);
        aqVar.a(B);
        return aqVar;
    }

    static aq<JsonDeserializer<?>> b() {
        return C;
    }

    static aq<InstanceCreator<?>> c() {
        return D;
    }
}
