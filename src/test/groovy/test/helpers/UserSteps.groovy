package test.helpers

import beakers.domain.system.User
import beakers.service.app.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import test.pages.LoginPage
import test.pages.StrategyPage

@Component
class UserSteps extends AbstractSteps {

    @Autowired
    UserService userService

    @Transactional
    User createUser(String username = "user1", String password = "passwd") {
        def player = userService.createUser(username, password, username + "@test.ru", "Test User")
        return player
    }

    void login(String username = "user1", String password = "passwd") {
        to LoginPage
        usernameField = username
        passwordField = password
        submitButton.click()
        at StrategyPage
    }
}
