package beakers.jobs.utils

import beakers.jobs.job.JobManager
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.Trigger

import java.util.concurrent.ScheduledFuture

/**
 * Notifies job manager about all scheduled jobs
 */
class DelegatingScheduler implements TaskScheduler {
    TaskScheduler scheduler
    JobManager jobManager

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Trigger trigger) {
        def managed = new ManagedScheduledMethodRunnable(task)
        jobManager?.addCron(managed, trigger)
        return scheduler?.schedule(managed, trigger)
    }

    @Override
    public ScheduledFuture<?> schedule(Runnable task, Date startTime) {
        def managed = new ManagedScheduledMethodRunnable(task)
        jobManager?.addOnce(managed, startTime)
        return scheduler?.schedule(managed, startTime)
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, Date startTime, long period) {
        def managed = new ManagedScheduledMethodRunnable(task)
        jobManager?.addRate(managed, startTime, period)
        return scheduler?.scheduleAtFixedRate(managed, startTime, period)
    }

    @Override
    public ScheduledFuture<?> scheduleAtFixedRate(Runnable task, long period) {
        def managed = new ManagedScheduledMethodRunnable(task)
        jobManager?.addRate(managed, new Date(), period)
        return scheduler?.scheduleAtFixedRate(managed, period)
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, Date startTime, long delay) {
        def managed = new ManagedScheduledMethodRunnable(task)
        jobManager?.addDelay(managed, startTime, delay)
        return scheduler?.scheduleWithFixedDelay(managed, startTime, delay)
    }

    @Override
    public ScheduledFuture<?> scheduleWithFixedDelay(Runnable task, long delay) {
        def managed = new ManagedScheduledMethodRunnable(task)
        jobManager?.addDelay(managed, new Date(), delay)
        return scheduler?.scheduleWithFixedDelay(managed, delay)
    }
}
