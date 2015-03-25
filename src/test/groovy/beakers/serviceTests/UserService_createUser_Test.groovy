package beakers.serviceTests

import beakers.auth.domain.User
import beakers.auth.errors.EmailAlreadyExists
import beakers.auth.errors.UsernameAlreadyExists
import beakers.auth.service.UserService
import junit.framework.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import beakers.AbstractSpringTest

public class UserService_createUser_Test extends AbstractSpringTest {

    @Autowired
    UserService userService

    @Test
    public void 'creating player'() {
        given:
        userService.createUser("user1", "paswd", "asd@asd.ru", "Test User")

        when:
        userService.createUser("user1", "paswd", "asd@asd.ru", "Test User")
        then:
        thrown(UsernameAlreadyExists)

        when:
        userService.createUser("user2", "paswd", "asd@asd.ru", "Another Test User")
        then:
        thrown(EmailAlreadyExists)

        when:
        userService.createUser("user2", "paswd", "asd2@asd.ru", "Another Test User")
        then:
        def user = User.findByUsername("user2")
        Assert.assertNotNull("user", user)
        Assert.assertEquals("user name", "Another Test User", user.fullName)
        Assert.assertEquals("user email", "asd2@asd.ru", user.email)
    }

}