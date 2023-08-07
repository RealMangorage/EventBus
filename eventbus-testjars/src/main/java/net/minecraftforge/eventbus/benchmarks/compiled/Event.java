package net.minecraftforge.eventbus.benchmarks.compiled;

import net.minecraftforge.eventbus.api.EventPriority;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class Event<T> {

    public static <X> Event<X> create(Class<X> type, Function<X[][], X> invokerFactory) {
        return new Event<>(type, invokerFactory);
    }



    protected volatile T invoker;
    private final Object lock = new Object();
    private final Function<T[][], T> invokerFactory;
    private T[][] handlers;

    @SuppressWarnings("unchecked")
    protected Event(Class<T> type, Function<T[][], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.handlers = (T[][]) Array.newInstance(type, EventPriority.values().length, 0);
        update();
    }

    public T invoker() {
        return invoker;
    }

    public void register(EventPriority priority, T listener) {
        Objects.requireNonNull(listener, "Tried to register null Listener");
        int i = priority.ordinal();
        synchronized (lock) {
            rebuild(i, handlers[i].length + 1);
            handlers[i][handlers[i].length - 1] = listener;
        }
    }

    private void rebuild(int priority, int newLength) {
        handlers[priority] = Arrays.copyOf(handlers[priority], handlers[priority].length + 1);
        update();
    }

    private void update() {
        this.invoker = invokerFactory.apply(handlers);
    }
}