package beakers.helpers

import beakers.auth.domain.User
import beakers.auth.service.UserService
import beakers.pages.HomePage
import beakers.pages.LoginPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

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
        at HomePage
    }
}
