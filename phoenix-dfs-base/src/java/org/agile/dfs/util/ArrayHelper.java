package org.agile.dfs.util;

import java.lang.reflect.Array;

@SuppressWarnings("unchecked")
public class ArrayHelper<T> {

    private int size = 0;
    private T[] data;

    public ArrayHelper() {
        data = (T[]) new Object[4];
    }

    public void add(T item) {
        if (size >= data.length) {
            T[] tmp = (T[]) new Object[data.length + data.length / 2];
            System.arraycopy(data, 0, tmp, 0, size);
            data = tmp;
        }
        data[size++] = item;
    }

    public T[] array(Class clz) {
        if (size > 0) {
            T[] tmp = (T[]) Array.newInstance(clz, size);
            System.arraycopy(data, 0, tmp, 0, size);
            return tmp;
        } else {
            return (T[]) Array.newInstance(clz, 0);
        }
    }
}
