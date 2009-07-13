package org.agile.dfs.rpc.util;

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
        }
        data[size++] = item;
    }

    public T[] array() {
        if (size == data.length) {
            return data;
        } else {
            T[] tmp = (T[]) new Object[size];
            System.arraycopy(data, 0, tmp, 0, size);
            return tmp;
        }
    }
}
