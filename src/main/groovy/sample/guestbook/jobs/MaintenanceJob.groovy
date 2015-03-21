package sample.guestbook.jobs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import sample.guestbook.services.GuestBookService

@Service
public class MaintenanceJob {

    @Autowired
    GuestBookService exampleService

    @Scheduled(cron = '${cron.cleaner:0 0 0 * * *}')
    public void execute() {
        exampleService.executeJob()
    }
}
