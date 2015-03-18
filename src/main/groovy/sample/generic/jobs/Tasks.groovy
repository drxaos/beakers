package sample.generic.jobs

import beakers.system.events.EventBus
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import sample.generic.events.AnonymousEvent
import sample.generic.events.SampleEvent

@Log4j
@Configuration
class Tasks {

    @Autowired
    EventBus eventBus

    @Scheduled(fixedRate = 5000l)
    public void sampleTask() {
        eventBus.publish(new SampleEvent(payload: "time: ${System.currentTimeMillis()}"))
    }

    @Scheduled(fixedDelay = 10000l)
    public void sampleDelayTask() {
        eventBus.publish(new AnonymousEvent())
    }

    @Scheduled(cron = "0 0 * * * *")
    public void dummyCronTask() {
        // nothing
    }

}
