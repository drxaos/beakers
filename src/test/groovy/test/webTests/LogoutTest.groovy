package test.webTests

import blotto.test.AbstractSpringTest
import blotto.test.helpers.PlayerSteps
import blotto.test.pages.LoginPage
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import test.AbstractSpringTest
import test.helpers.UserSteps

public class LogoutTest extends AbstractSpringTest {

    @Autowired
    UserSteps userSteps

    @Test
    public void 'Successful logout'() {
        given: "Player exists"
        userSteps.createPlayer()
        and: "Player is logged in"
        userSteps.login()

        when: "Player logs out"
        userSteps.click()

        then: "Player is at the login page"
        at LoginPage
    }
}