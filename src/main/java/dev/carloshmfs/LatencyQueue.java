package dev.carloshmfs;

import it.unimi.dsi.fastutil.ints.IntArrayList;

public class LatencyQueue {
    private final int maxSize;
    public final IntArrayList list;

    LatencyQueue(int maxSize) {
        this.maxSize = maxSize;
        this.list = new IntArrayList(this.maxSize);
    }

    public void add(int value) {
        if (this.list.size() == this.maxSize) {
            this.list.remove(0);
        }

        this.list.add(value);
    }
}
