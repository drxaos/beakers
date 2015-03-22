package sample.guestbook.jobs

import beakers.system.service.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Description
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import sample.guestbook.services.GuestBookService

@Service
public class RevertJob {

    @Autowired
    UserService userService

    @Scheduled(cron = '${cron.cleaner:0 0 * * * *}')
    @Transactional
    @Description("Reverts database to clean state")
    public void execute() {
        userService.setPassword("admin", "admin")
        userService.setRoleGroup("admin", "admin")
        userService.setPassword("user", "user")
        userService.setRoleGroup("user", "user")

        // TODO remove other users
        // TODO remove guestbook history
        // TODO enable all jobs
    }
}
