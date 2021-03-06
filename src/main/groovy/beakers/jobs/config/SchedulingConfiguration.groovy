package beakers.jobs.config

import beakers.jobs.job.JobManager
import beakers.jobs.utils.DelegatingScheduler
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.SchedulingConfigurer
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler
import org.springframework.scheduling.config.ScheduledTaskRegistrar

import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

/**
 * Scheduling configuration and task registry holder
 */
@Log4j
@Configuration
@EnableScheduling
class SchedulingConfiguration implements SchedulingConfigurer {

    @Autowired
    ApplicationContext applicationContext

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        def scheduler = new ConcurrentTaskScheduler(((ScheduledExecutorService) taskExecutor()))
        taskRegistrar.setScheduler(new DelegatingScheduler(scheduler: scheduler, jobManager: jobManger()));
    }

    @Bean(destroyMethod = "shutdown")
    public Executor taskExecutor() {
        return Executors.newScheduledThreadPool(100);
    }

    @Bean
    public JobManager jobManger() {
        return new JobManager()
    }
}
