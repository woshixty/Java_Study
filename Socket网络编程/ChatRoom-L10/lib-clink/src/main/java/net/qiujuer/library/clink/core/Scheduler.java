package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public interface Scheduler extends Closeable {
    /**
     * 调度一份延迟任务
     * @param runnable
     * @param delay
     * @param unit
     * @return
     */
    ScheduledFuture<?> schedule(Runnable runnable, long delay, TimeUnit unit);

    void delivery(Runnable runnable);
}
