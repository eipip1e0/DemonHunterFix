package defpackage;

import java.lang.ref.WeakReference;

/* renamed from: x  reason: default package */
public final class x implements Runnable {
    private WeakReference<d> a;

    public x(d dVar) {
        this.a = new WeakReference<>(dVar);
    }

    public final void run() {
        d dVar = this.a.get();
        if (dVar == null) {
            t.a("The ad must be gone, so cancelling the refresh timer.");
        } else {
            dVar.s();
        }
    }
}
