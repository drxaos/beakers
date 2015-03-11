package sample.guestbook.jobs

import beakers.system.job.AbstractJob
import sample.guestbook.services.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
public class ExampleJob extends AbstractJob {

    @Autowired
    ExampleService exampleService

    @Scheduled(cron = '${cron.cleaner:0 0 0 * * *}')
    public void execute() {
        run { exampleService.executeJob() }
    }
}
