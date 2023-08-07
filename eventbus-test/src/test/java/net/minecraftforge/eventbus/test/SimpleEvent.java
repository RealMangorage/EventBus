package net.minecraftforge.eventbus.test;

import net.minecraftforge.eventbus.api.Event;

public class SimpleEvent extends Event {
    private final String data;
    public SimpleEvent(String data) {
        this.data = data;
    }

    public String get() {
        return data;
    }
}
