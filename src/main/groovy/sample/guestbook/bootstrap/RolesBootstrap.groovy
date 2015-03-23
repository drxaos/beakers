package sample.guestbook.bootstrap

import beakers.system.bootstrap.BeakersBootstrap
import beakers.system.errors.domain.ServiceException
import beakers.system.service.auth.UserService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component

@Log4j
@Component
class RolesBootstrap implements BeakersBootstrap {

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
    }
}
