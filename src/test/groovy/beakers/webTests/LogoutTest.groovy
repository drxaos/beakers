package beakers.webTests

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import beakers.AbstractSpringTest
import beakers.helpers.UserSteps
import beakers.pages.HomePage

public class LogoutTest extends AbstractSpringTest {

    @Autowired
    UserSteps userSteps

    @Test
    public void 'Successful logout'() {
        given: "Player exists"
        userSteps.createUser()
        and: "Player is logged in"
        userSteps.login()

        when: "Player logs out"
        logout.click()

        then: "Player is at the home page"
        at HomePage
    }
}