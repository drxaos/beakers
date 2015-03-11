package sample.guestbook.jobs

import beakers.system.job.AbstractJob
import sample.guestbook.services.GuestBookService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
public class MaintenanceJob extends AbstractJob {

    @Autowired
    GuestBookService exampleService

    @Scheduled(cron = '${cron.cleaner:0 0 0 * * *}')
    public void execute() {
        run { exampleService.executeJob() }
    }
}
