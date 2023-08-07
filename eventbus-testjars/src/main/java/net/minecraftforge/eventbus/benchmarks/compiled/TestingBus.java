package net.minecraftforge.eventbus.benchmarks.compiled;

import net.minecraftforge.eventbus.BusBuilderImpl;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;

public class TestingBus {
    public static void main(int sampleSize, int listeners, long FITime) {
        long time = 0;

        for (int i = 0; i < sampleSize; i++) {
            Util.gc();
            time += new TestingBus().main(listeners);
        }

        System.out.println("EventBus Results: running sample size of %s with %s listeners per sample, posting 1 Event".formatted(sampleSize, listeners));
        System.out.println("Total Nanoseconds:                %s".formatted(time));
        System.out.println("Total AVG Nanoseconds:            %s".formatted(time/sampleSize));
        System.out.println("Total Nanoseconds difference:     %s".formatted(time - FITime));
        System.out.println("Total AVG Nanoseconds difference: %s".formatted((time/sampleSize) - (FITime/sampleSize)));
    }

    public final IEventBus BUS = new BusBuilderImpl().setTrackPhases(true).build();


    public long main(int limit) {
        BUS.start();
        for (int i = 0; i < limit; i++ ){
            BUS.addListener(EventPriority.NORMAL, false, TestEvent.class, (test) -> {});
        }
        return getTimeTaken();
    }

    public void test(TestEvent event) {
    }

    public long getTimeTaken() {
        long time = System.nanoTime();
        BUS.post(new TestEvent());
        return System.nanoTime() - time;
    }

}
