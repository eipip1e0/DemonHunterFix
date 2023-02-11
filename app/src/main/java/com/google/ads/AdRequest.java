package com.google.ads;

import android.location.Location;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdRequest {
    public static final String LOGTAG = "Ads";
    public static final String VERSION = "4.0.4";
    private Gender a = null;
    private String b = null;
    private Set<String> c = null;
    private Map<String, Object> d = null;
    private Location e = null;
    private boolean f = false;

    public enum ErrorCode {
        INVALID_REQUEST("Invalid Google Ad request."),
        NO_FILL("No ad to show."),
        NETWORK_ERROR("A network error occurred."),
        INTERNAL_ERROR("There was an internal error.");
        
        private String a;

        private ErrorCode(String description) {
            this.a = description;
        }

        public final String toString() {
            return this.a;
        }
    }

    public enum Gender {
        MALE(AdActivity.TYPE_PARAM),
        FEMALE("f");
        
        private String a;

        private Gender(String param) {
            this.a = param;
        }

        public final String toString() {
            return this.a;
        }
    }

    public void addExtra(String key, Object value) {
        if (this.d == null) {
            this.d = new HashMap();
        }
        this.d.put(key, value);
    }

    public void addKeyword(String keyword) {
        if (this.c == null) {
            this.c = new HashSet();
        }
        this.c.add(keyword);
    }

    public Map<String, Object> getRequestMap() {
        HashMap hashMap = new HashMap();
        if (this.c != null) {
            hashMap.put("kw", this.c);
        }
        if (this.a != null) {
            hashMap.put("cust_gender", this.a.toString());
        }
        if (this.b != null) {
            hashMap.put("cust_age", this.b);
        }
        if (this.e != null) {
            hashMap.put("uule", u.a(this.e));
        }
        if (this.f) {
            hashMap.put("testing", 1);
        }
        if (this.d != null) {
            hashMap.put("extras", this.d);
        }
        return hashMap;
    }

    public void setBirthday(String birthday) {
        this.b = birthday;
    }

    public void setExtras(Map<String, Object> extras) {
        this.d = extras;
    }

    public void setGender(Gender gender) {
        this.a = gender;
    }

    public void setKeywords(Set<String> keywords) {
        this.c = keywords;
    }

    public void setLocation(Location location) {
        this.e = location;
    }

    public void setTesting(boolean testing) {
        this.f = testing;
    }
}
