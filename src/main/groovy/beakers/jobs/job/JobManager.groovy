package beakers.jobs.job

import beakers.jobs.utils.JobUtils
import beakers.jobs.utils.ManagedScheduledMethodRunnable
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Description
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.support.CronTrigger

class JobManager {

    @Value('${scheduler.enable:true}')
    Boolean schedulerEnabled

    class Task {
        String type // cron, once, delay, rate
        CronTrigger cron
        Date start
        Long period
        String description

        ManagedScheduledMethodRunnable runnable

        boolean getEnabled() {
            return runnable.enabled
        }

        void setEnabled(boolean enabled) {
            runnable.enabled = enabled
        }

        def getName() {
            return runnable.method.declaringClass.simpleName + "." + runnable.method.name
        }

        def getFullName() {
            return runnable.method.toString()
        }

        def getNextRun() {
            return cron ? JobUtils.getNextRun(cron.expression) : ""
        }

        def getLastStart() {
            return runnable.lastStart
        }

        def getLastEnd() {
            return runnable.lastEnd
        }

        def getInProgress() {
            return runnable.inProgress
        }

        def getExpression() {
            return cron ? cron.expression : ""
        }
    }

    List<Task> tasks = []

    private String getDescription(ManagedScheduledMethodRunnable r) {
        return r.method.getAnnotation(Description)?.value() ?: ""
    }

    def addCron(ManagedScheduledMethodRunnable runnable, Trigger trigger) {
        runnable.enabled = schedulerEnabled
        tasks << new Task(
                runnable: runnable,
                type: "cron",
                cron: (CronTrigger) trigger,
                description: getDescription(runnable)
        )
    }


    def addOnce(ManagedScheduledMethodRunnable runnable, Date date) {
        runnable.enabled = schedulerEnabled
        tasks << new Task(
                runnable: runnable,
                type: "once",
                start: date,
                description: getDescription(runnable)
        )
    }

    def addRate(ManagedScheduledMethodRunnable runnable, Date date, long l) {
        runnable.enabled = schedulerEnabled
        tasks << new Task(
                runnable: runnable,
                type: "rate",
                start: date,
                period: l,
                description: getDescription(runnable)
        )
    }

    def addDelay(ManagedScheduledMethodRunnable runnable, Date date, long l) {
        runnable.enabled = schedulerEnabled
        tasks << new Task(
                runnable: runnable,
                type: "delay",
                start: date,
                period: l,
                description: getDescription(runnable)
        )
    }

    boolean execute(String fullName) {
        def task = tasks.find { it.fullName == fullName }
        if (task) {
            task.runnable.run(true)
            return true
        } else {
            return false
        }
    }

    boolean setEnabled(String fullName, boolean enabled) {
        def task = tasks.find { it.fullName == fullName }
        if (task) {
            task.runnable.enabled = enabled
            return true
        } else {
            return false
        }
    }
}
