package beakers.system.bootstrap

import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

/**
 * Bootstrap executes on application startup
 */
@Log4j
@Component
@Profile(["dev", "prod", "test"])
public class ApplicationBootstrapExecutor implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ApplicationContext applicationContext

    private boolean initialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized) {
            return;
        }
        initialized = true;

        applicationContext.getBeansOfType(BeakersBootstrap).each { String name, BeakersBootstrap b ->
            log.info("Executing bootstrap: ${name}")
            b.bootstrap()
        }
    }
}