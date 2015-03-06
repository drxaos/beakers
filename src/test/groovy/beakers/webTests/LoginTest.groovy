package beakers.webTests

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import beakers.AbstractSpringTest
import beakers.helpers.UserSteps
import beakers.pages.HomePage
import beakers.pages.LoginPage

public class LoginTest extends AbstractSpringTest {

    @Autowired
    UserSteps userSteps

    @Test
    public void 'Successful login'() {
        given: "Player exists"
        userSteps.createUser()
        and: "Player is at home page"
        to HomePage

        when: "Player clicks login"
        login.click()
        then: "Player is at the login page"
        at LoginPage

        when: "Player logins as a valid user"
        usernameField = "user1"
        passwordField = "passwd"
        submitButton.click()

        then: "Player is at the home page"
        at HomePage
        and: "Player sees username"
        assert username == "Test User"
    }

    @Test
    def "Invalid user login"() {
        given: "Player exists"
        userSteps.createUser()
        and: "Player is at the login page"
        to LoginPage

        when: "Player logins as invalid user"
        usernameField = "qwerty"
        passwordField = "passwd"
        submitButton.click()

        then: "Player is at the login page with error alert"
        at LoginPage
        assert alertText == "Wrong credentials"
    }

    @Test
    def "Invalid password login"() {
        given: "Player exists"
        userSteps.createUser()
        and: "Player is at the login page"
        to LoginPage

        when: "Player logins with invalid password"
        usernameField = "user1"
        passwordField = "incorrect"
        submitButton.click()

        then: "Player is at the login page with error alert"
        at LoginPage
        assert alertText == "Wrong credentials"
    }
}