package com.bicore;

import java.util.Iterator;

public class IntHashMap<T> {
    private int capacity;
    IntHashMap<T>.EntryIterable iterable;
    private float loadFactor;
    private int mask;
    private int size;
    private Entry[] table;
    private int threshold;

    public IntHashMap() {
        this(16, 0.75f);
    }

    public IntHashMap(int initialCapacity) {
        this(initialCapacity, 0.75f);
    }

    public IntHashMap(int initialCapacity, float loadFactor2) {
        this.iterable = new EntryIterable();
        if (initialCapacity > 1073741824) {
            throw new IllegalArgumentException("initialCapacity is too large.");
        } else if (initialCapacity < 0) {
            throw new IllegalArgumentException("initialCapacity must be greater than zero.");
        } else if (loadFactor2 <= 0.0f) {
            throw new IllegalArgumentException("initialCapacity must be greater than zero.");
        } else {
            this.capacity = 1;
            while (this.capacity < initialCapacity) {
                this.capacity <<= 1;
            }
            this.loadFactor = loadFactor2;
            this.threshold = (int) (((float) this.capacity) * loadFactor2);
            this.table = new Entry[this.capacity];
            this.mask = this.capacity - 1;
        }
    }

    public boolean containsValue(Object value) {
        Entry[] table2 = this.table;
        int i = table2.length;
        while (true) {
            i--;
            if (i <= 0) {
                return false;
            }
            for (Entry e = table2[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return true;
                }
            }
        }
    }

    public boolean containsKey(long key) {
        for (Entry e = this.table[((int) key) & this.mask]; e != null; e = e.next) {
            if (((long) e.key) == key) {
                return true;
            }
        }
        return false;
    }

    public T get(int key) {
        for (Entry e = this.table[key & this.mask]; e != null; e = e.next) {
            if (e.key == key) {
                return (T) e.value;
            }
        }
        return null;
    }

    public T put(int key, T value) {
        int index = key & this.mask;
        for (Entry e = this.table[index]; e != null; e = e.next) {
            if (e.key == key) {
                T t = (T) e.value;
                e.value = value;
                return t;
            }
        }
        this.table[index] = new Entry(key, value, this.table[index]);
        int i = this.size;
        this.size = i + 1;
        if (i >= this.threshold) {
            int newCapacity = this.capacity * 2;
            Entry[] newTable = new Entry[newCapacity];
            Entry[] src = this.table;
            int bucketmask = newCapacity - 1;
            for (int j = 0; j < src.length; j++) {
                Entry e2 = src[j];
                if (e2 != null) {
                    src[j] = null;
                    do {
                        Entry next = e2.next;
                        int index2 = e2.key & bucketmask;
                        e2.next = newTable[index2];
                        newTable[index2] = e2;
                        e2 = next;
                    } while (e2 != null);
                }
            }
            this.table = newTable;
            this.capacity = newCapacity;
            this.threshold = (int) (((float) newCapacity) * this.loadFactor);
            this.mask = this.capacity - 1;
        }
        return null;
    }

    public T remove(int key) {
        int index = key & this.mask;
        Entry prev = this.table[index];
        Entry e = prev;
        while (e != null) {
            Entry next = e.next;
            if (e.key == key) {
                this.size--;
                if (prev == e) {
                    this.table[index] = next;
                } else {
                    prev.next = next;
                }
                return (T) e.value;
            }
            prev = e;
            e = next;
        }
        return null;
    }

    public int getKey(Object value) {
        Entry[] table2 = this.table;
        int i = table2.length;
        while (true) {
            i--;
            if (i <= 0) {
                return 0;
            }
            for (Entry e = table2[i]; e != null; e = e.next) {
                if (e.value.equals(value)) {
                    return e.key;
                }
            }
        }
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        Entry[] table2 = this.table;
        int index = table2.length;
        while (true) {
            index--;
            if (index < 0) {
                this.size = 0;
                return;
            }
            table2[index] = null;
        }
    }

    /* access modifiers changed from: package-private */
    public static class Entry {
        final int key;
        Entry next;
        Object value;

        Entry(int k, Object v, Entry n) {
            this.key = k;
            this.value = v;
            this.next = n;
        }
    }

    public Iterable<T> values() {
        this.iterable.reset();
        return this.iterable;
    }

    /* access modifiers changed from: package-private */
    public class EntryIterable implements Iterable<T> {
        Entry currEntry = null;
        int currIndex = -1;
        Iterator<T> iter = new Iterator<T>() {
            /* class com.bicore.IntHashMap.EntryIterable.AnonymousClass1 */

            public boolean hasNext() {
                if (EntryIterable.this.currEntry == null) {
                    if (!loadNextEntry()) {
                        return false;
                    }
                } else if (EntryIterable.this.currEntry.next == null && !loadNextEntry()) {
                    return false;
                }
                return true;
            }

            private boolean loadNextEntry() {
                do {
                    EntryIterable.this.currIndex++;
                    if (EntryIterable.this.currIndex >= IntHashMap.this.table.length) {
                        return false;
                    }
                } while (IntHashMap.this.table[EntryIterable.this.currIndex] == null);
                EntryIterable.this.currEntry = IntHashMap.this.table[EntryIterable.this.currIndex];
                return true;
            }

            @Override // java.util.Iterator
            public T next() {
                return (T) EntryIterable.this.currEntry.value;
            }

            public void remove() {
                throw new UnsupportedOperationException("not implemented");
            }
        };

        EntryIterable() {
        }

        public void reset() {
            this.currIndex = -1;
            this.currEntry = null;
        }

        @Override // java.lang.Iterable
        public Iterator<T> iterator() {
            return this.iter;
        }
    }
}
