package net.minecraftforge.eventbus.test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

public class Event<T> {

    public static <X> Event<X> create(Class<X> type, Function<X[], X> invokerFactory) {
        return new Event<>(type, invokerFactory);
    }



    protected volatile T invoker;
    private final Object lock = new Object();
    private final Function<T[], T> invokerFactory;
    private T[] handlers;

    @SuppressWarnings("unchecked")
    protected Event(Class<T> type, Function<T[], T> invokerFactory) {
        this.invokerFactory = invokerFactory;
        this.handlers = (T[]) Array.newInstance(type, 0);
        update();
    }

    public T invoker() {
        return invoker;
    }

    public void register(T listener) {
        Objects.requireNonNull(listener, "Tried to register null Listener");
        synchronized (lock) {
            rebuild(handlers.length + 1);
            handlers[handlers.length - 1] = listener;
        }
    }

    private void rebuild(int newLength) {
        handlers = Arrays.copyOf(handlers, handlers.length + 1);
        update();
    }

    private void update() {
        this.invoker = invokerFactory.apply(handlers);
    }
}