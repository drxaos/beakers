package beakers.jobs.utils

import groovy.util.logging.Log4j
import org.joda.time.DateTime
import org.springframework.scheduling.support.ScheduledMethodRunnable

import java.lang.reflect.Method
import java.util.concurrent.locks.ReentrantLock

@Log4j
class ManagedScheduledMethodRunnable implements Runnable {

    final private lock = new ReentrantLock()
    private Date lastStart = null
    private Date lastEnd = null
    public boolean enabled = true

    ScheduledMethodRunnable runnable

    ManagedScheduledMethodRunnable(ScheduledMethodRunnable runnable) {
        this.runnable = runnable
    }

    public Object getTarget() {
        return runnable.target
    }

    public Method getMethod() {
        return runnable.method
    }


    public boolean isInProgress() {
        return lock.locked
    }

    Date getLastStart() {
        return lastStart
    }

    Date getLastEnd() {
        return lastEnd
    }

    void run(boolean force = false) {
        if (!enabled && !force) {
            return
        }
        try {
            lock.lock()
            lastStart = DateTime.now().toDate()

            runnable.run()

            lastEnd = DateTime.now().toDate()
        } catch (Exception e) {
            log.error(null, e)
        } finally {
            lock.unlock()
        }
    }
}
