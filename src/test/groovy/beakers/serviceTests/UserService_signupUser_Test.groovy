package beakers.serviceTests

import beakers.auth.domain.User
import beakers.auth.errors.EmailAlreadyExists
import beakers.auth.errors.UsernameAlreadyExists
import beakers.auth.service.UserService
import beakers.mail.utils.SmtpStubServer
import grails.util.Holders
import junit.framework.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import beakers.AbstractSpringTest

public class UserService_signupUser_Test extends AbstractSpringTest {

    @Autowired
    SmtpStubServer smtpStubServer

    @Autowired
    UserService userService

    @Test
    public void 'signing up player'() {

        given: "there is one player already"
        userService.createUser("user1", "paswd", "asd@asd.ru", "Test User")

        when: "user uses existing username"
        smtpStubServer.reset()
        userService.signUpUser("user1", "paswd", "asd@asd.ru", "Test User")

        then: "registration fails"
        thrown(UsernameAlreadyExists)
        assert smtpStubServer.allMessages.size() == 0

        when: "user uses existing email"
        smtpStubServer.reset()
        userService.signUpUser("user2", "paswd", "asd@asd.ru", "Another Test User")

        then: "registration fails"
        thrown(EmailAlreadyExists)
        assert smtpStubServer.allMessages.size() == 0

        when: "user signs up correctly"
        smtpStubServer.reset()
        userService.signUpUser("user2", "paswd", "asd2@asd.ru", "Another Test User")

        then: "there is a new user"
        def user = User.findByUsername("user2")
        Assert.assertNotNull("user", user)
        Assert.assertEquals("user name", "Another Test User", user.fullName)
        Assert.assertEquals("user email", "asd2@asd.ru", user.email)

        and: "there is a sign up mail"
        assert smtpStubServer.allMessages.size() == 1
        def mail = smtpStubServer.allMessages.first()
        assert mail.from == "admin@${Holders.config.app.host}"
        assert mail.to == "asd2@asd.ru"
        assert mail.subject == "Registration at ${Holders.config.app.host}"
        assert mail.plainText.contains("Welcome, Another Test User")
    }

}