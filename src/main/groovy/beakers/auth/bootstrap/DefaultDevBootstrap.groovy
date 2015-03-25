package beakers.auth.bootstrap

import beakers.system.bootstrap.BeakersBootstrap
import beakers.system.errors.ServiceException
import beakers.auth.service.UserService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Log4j
@Component
@Profile(["dev"])
class DefaultDevBootstrap implements BeakersBootstrap {

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    UserService userService

    @Override
    def bootstrap() {
        try {
            userService.createRoleGroup("user", ["ROLE_USER"])
        } catch (ServiceException e) {
            log.info(e)
        }
        try {
            userService.createRoleGroup("admin", ["ROLE_USER", "ROLE_ADMIN"])
        } catch (ServiceException e) {
            log.info(e)
        }
        try {
            userService.createUser("user", "user", "user@example.com", "User for test")
        } catch (ServiceException e) {
            log.info(e)
        }
        try {
            userService.createUser("admin", "admin", "admin@example.com", "User for test", "admin")
        } catch (ServiceException e) {
            log.info(e)
        }
    }
}
