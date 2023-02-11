package com.google.gson;

import java.util.Iterator;
import java.util.Stack;

/* access modifiers changed from: package-private */
public final class aj {
    private final Stack<ao> a = new Stack<>();

    aj() {
    }

    public final ao a() {
        return this.a.pop();
    }

    public final ao a(ao aoVar) {
        at.a(aoVar);
        return this.a.push(aoVar);
    }

    public final boolean b(ao aoVar) {
        if (aoVar == null) {
            return false;
        }
        Iterator<ao> it = this.a.iterator();
        while (it.hasNext()) {
            ao next = it.next();
            if (next.a() == aoVar.a() && next.a.equals(aoVar.a)) {
                return true;
            }
        }
        return false;
    }
}
