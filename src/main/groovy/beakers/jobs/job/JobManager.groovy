package beakers.jobs.job

import beakers.jobs.utils.JobUtils
import org.springframework.context.annotation.Description
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.support.CronTrigger
import org.springframework.scheduling.support.ScheduledMethodRunnable

class JobManager {
    class Task {
        String type // cron, once, delay, rate
        CronTrigger cron
        Date start
        Long period
        String description

        ScheduledMethodRunnable runnable

        def getName() {
            return runnable.method.declaringClass.simpleName + "." + runnable.method.name
        }

        def getFullName() {
            return runnable.method.toString()
        }

        def getNextRun() {
            return cron ? JobUtils.getNextRun(cron.expression) : ""
        }

        def getExpression() {
            return cron ? cron.expression : ""
        }
    }

    class Job {
        AbstractJob target
        CronTrigger trigger
        ScheduledMethodRunnable runnable
        String description

        def getName() {
            return runnable.method.declaringClass.simpleName + "." + runnable.method.name
        }

        def getFullName() {
            return runnable.method.toString()
        }

        def getNextRun() {
            return JobUtils.getNextRun(trigger.expression)
        }

        def getLastStart() {
            return target.lastStart
        }

        def getLastEnd() {
            return target.lastEnd
        }

        def getInProgress() {
            return target.inProgress
        }

        def getExpression() {
            return trigger.expression
        }
    }

    List<Task> tasks = []
    List<Job> jobs = []

    def addCron(Runnable runnable, Trigger trigger) {
        def target = ((ScheduledMethodRunnable) runnable).target
        def desc = ((ScheduledMethodRunnable) runnable).method.getAnnotation(Description)?.value() ?: ""
        if (target instanceof AbstractJob) {
            jobs << new Job(runnable: (ScheduledMethodRunnable) runnable, target: target, trigger: (CronTrigger) trigger, description: desc)
        } else {
            tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "cron", cron: (CronTrigger) trigger, description: desc)
        }
    }

    def addOnce(Runnable runnable, Date date) {
        def desc = ((ScheduledMethodRunnable) runnable).method.getAnnotation(Description)?.value() ?: ""
        tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "once", start: date, description: desc)
    }

    def addRate(Runnable runnable, Date date, long l) {
        def desc = ((ScheduledMethodRunnable) runnable).method.getAnnotation(Description)?.value() ?: ""
        tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "rate", start: date, period: l, description: desc)
    }

    def addDelay(Runnable runnable, Date date, long l) {
        def desc = ((ScheduledMethodRunnable) runnable).method.getAnnotation(Description)?.value() ?: ""
        tasks << new Task(runnable: (ScheduledMethodRunnable) runnable, type: "delay", start: date, period: l, description: desc)
    }
}
