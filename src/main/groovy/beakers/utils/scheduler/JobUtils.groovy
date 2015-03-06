package beakers.utils.scheduler

import org.joda.time.DateTime
import org.springframework.scheduling.support.CronSequenceGenerator

class JobUtils {

    /**
     * Calculates next run date
     * @param cronExpression Cron expression
     * @return Date of the next run
     */
    static Date getNextRun(String cronExpression) {
        return new CronSequenceGenerator(cronExpression).next(DateTime.now().toDate())
    }
}
