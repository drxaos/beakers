package sample.guestbook.jobs

import beakers.system.errors.ServiceException
import beakers.auth.service.UserService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Description
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class RevertJob {

    @Autowired
    UserService userService

    @Scheduled(cron = '${cron.cleaner:0 0 * * * *}')
    @Transactional
    @Description("Reverts database to clean state")
    public void execute() {
        try {
            userService.createUser("admin", "admin", "admin@example.com", "Admin", "admin")
        } catch (ServiceException e) {
            log.info(e)
        }
        try {
            userService.setPassword("admin", "admin")
        } catch (Exception e) {
            log.error(null, e)
        }
        try {
            userService.createRoleGroup("admin", ["ROLE_USER", "ROLE_ADMIN"])
        } catch (Exception ignore) {
        }
        try {
            userService.setRoleGroup("admin", "admin")
        } catch (Exception e) {
            log.error(null, e)
        }
        try {
            userService.createUser("user", "user", "user@example.com", "User for test")
        } catch (ServiceException e) {
            log.info(e)
        }
        try {
            userService.setPassword("user", "user")
        } catch (Exception e) {
            log.error(null, e)
        }
        try {
            userService.createRoleGroup("user", ["ROLE_USER"])
        } catch (Exception ignore) {
        }
        try {
            userService.setRoleGroup("user", "user")
        } catch (Exception e) {
            log.error(null, e)
        }

        // TODO remove other users
        // TODO remove guestbook history
        // TODO enable all jobs
    }
}
