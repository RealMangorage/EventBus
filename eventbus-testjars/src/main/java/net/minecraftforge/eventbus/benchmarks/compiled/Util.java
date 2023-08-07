package net.minecraftforge.eventbus.benchmarks.compiled;

import net.minecraftforge.eventbus.api.EventPriority;

import java.lang.ref.WeakReference;
import java.lang.reflect.Array;

public class Util {
    public static void gc() {
        Object obj = new Object();
        WeakReference ref = new WeakReference<Object>(obj);
        obj = null;
        while(ref.get() != null) {
            System.gc();
        }
    }

    public static void main(String[] args) {
        String[][] test = (String[][]) Array.newInstance(String.class, EventPriority.values().length, 0);
        System.out.println(test.length);
    }
}
