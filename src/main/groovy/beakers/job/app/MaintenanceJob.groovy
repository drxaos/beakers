package beakers.job.app

import beakers.job.system.AbstractJob
import beakers.service.app.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
public class MaintenanceJob extends AbstractJob {

    @Autowired
    ExampleService exampleService

    @Scheduled(cron = '${cron.cleaner:0 0 0 * * *}')
    public void execute() {
        run { exampleService.executeJob() }
    }
}
