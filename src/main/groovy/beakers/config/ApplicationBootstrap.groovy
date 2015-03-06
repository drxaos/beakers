package beakers.config

import beakers.errors.system.ServiceException
import beakers.service.app.auth.UserService
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
public class ApplicationBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ApplicationContext applicationContext

    private boolean initialized = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (initialized) {
            return;
        }
        initialized = true;
        def profiles = applicationContext.environment.activeProfiles ?: applicationContext.environment.defaultProfiles
        log.info("Active profiles: ${profiles}")
        log.info("Bootstrap")
        bootstrap(profiles as List)
    }

    @Autowired
    UserService userService

    public void bootstrap(List profiles) {

        if (profiles.contains("dev")) {
            try {
                userService.createRoleGroup("user", ["ROLE_USER"])
                userService.createRoleGroup("admin", ["ROLE_USER", "ROLE_ADMIN"])
            } catch (ServiceException e) {
                log.info(e)
            }
            try {
                userService.createUser("user", "user", "test@example.com", "User for test")
                userService.createUser("admin", "admin", "test@example.com", "User for test", "admin")
            } catch (ServiceException e) {
                log.info(e)
            }
        }
    }
}