package net.minecraftforge.eventbus.test;

import net.minecraftforge.eventbus.BusBuilderImpl;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import org.junit.jupiter.api.Test;

public class Testing {

    private static final IEventBus BUS = new BusBuilderImpl().setTrackPhases(false).build();

    public static final Event<EventA> EVENT_TEST = Event.create(EventA.class, callbacks -> (vol) -> {
        for (EventA callback : callbacks) {
            callback.done(vol);
        }
    });

    public interface EventA {
        void done(String vol);
    }

    @Test
    public void main() {
        BUS.start();
        for (int i = 0; i < 100000; i++ ){
            EVENT_TEST.register(System.out::println);
            BUS.addListener(EventPriority.NORMAL, false, SimpleEvent.class, (event) -> System.out.println(event.get()));
        }
        long a = getTimeTakenA();
        long b = getTimeTakenB();

        System.out.println("FI events: %s".formatted(a));

        System.out.println("EventBus events: %s".formatted(b));
    }

    public static long getTimeTakenA() {
        long time = System.currentTimeMillis();
        EVENT_TEST.invoker().done("Wow!!!!!");
        return System.currentTimeMillis() - time;
    }

    public static long getTimeTakenB() {
        long time = System.currentTimeMillis();
        BUS.post(new SimpleEvent("woooo!"));
        return System.currentTimeMillis() - time;
    }
}
