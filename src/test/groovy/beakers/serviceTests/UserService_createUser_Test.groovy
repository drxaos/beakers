package beakers.serviceTests

import beakers.system.domain.auth.User
import beakers.system.errors.domain.auth.EmailAlreadyExists
import beakers.system.errors.domain.auth.UsernameAlreadyExists
import beakers.system.service.auth.UserService
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