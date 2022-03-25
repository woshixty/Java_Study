package net.qiujuer.library.clink.impl;

import net.qiujuer.library.clink.core.Scheduler;

import java.io.IOException;
import java.util.concurrent.*;

public class SchedulerImpl implements Scheduler {
    private final ScheduledExecutorService scheduledExecutorService;
    private final ExecutorService deliveryPool;

    public SchedulerImpl(int poolSize) {
        scheduledExecutorService = Executors.newScheduledThreadPool(poolSize, new NameableThreadFactory(
                "Scheduler-Thread-"
        ));
        deliveryPool = Executors.newFixedThreadPool(4, new NameableThreadFactory(
                "Delivery-Thread-"
        ));
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit) {
        return scheduledExecutorService.schedule(runnable, delay, unit);
    }

    @Override
    public void delivery(Runnable runnable) {
        deliveryPool.execute(runnable);
    }

    @Override
    public void close() throws IOException {
        scheduledExecutorService.shutdown();
        deliveryPool.shutdown();
    }
}
