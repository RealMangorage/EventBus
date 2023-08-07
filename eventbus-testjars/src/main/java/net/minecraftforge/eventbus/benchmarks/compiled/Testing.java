package net.minecraftforge.eventbus.benchmarks.compiled;


import net.minecraftforge.eventbus.api.EventPriority;

import java.util.Arrays;

public class Testing {

    public static void main(String[] args) {
        int sampleSize = 15;
        int listeners = 100_000;
        long time = 0;

        for (int i = 0; i < sampleSize; i++) {
            Util.gc();
            time += new Testing().main(listeners);
        }

        System.out.println("FI Event Results: running sample size of %s with %s listeners per sample, posting 1 Event".formatted(sampleSize, listeners));
        System.out.println("Total Nanoseconds:                %s".formatted(time));
        System.out.println("Total AVG Nanoseconds:            %s".formatted(time/sampleSize));
        System.out.println(" ");
        TestingBus.main(sampleSize, listeners, time);
    }

    public final Event<EventA> EVENT_TEST = Event.create(EventA.class, callbacks -> (vol) -> {
        for (EventA[] priority : callbacks) {
            for (EventA callback : priority) {
                callback.done(vol);
            }
        }
    });

    public void test(String a) {
    }

    public interface EventA {
        void done(String vol);
    }

    public long main(int limit) {
        for (int i = 0; i < limit; i++ ){
            EVENT_TEST.register(EventPriority.HIGHEST, (e) -> {});
            EVENT_TEST.register(EventPriority.HIGHEST, this::test);
        }
        return getTimeTaken();
    }

    public long getTimeTaken() {
        long time = System.nanoTime();
        EVENT_TEST.invoker().done("Wow!!!!!");
        return System.nanoTime() - time;
    }

}
