package beakers.application.jobs

import groovy.util.logging.Log4j
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Log4j
@Configuration
class Tasks {

    @Scheduled(fixedRate = 1234l)
    public void dummyTask() {
        // nothing
    }

    @Scheduled(fixedDelay = 55555l)
    public void dummyDelayTask() {
        // nothing
    }

    @Scheduled(cron = "0 0 * * * *")
    public void dummyCronTask() {
        // nothing
    }

}
