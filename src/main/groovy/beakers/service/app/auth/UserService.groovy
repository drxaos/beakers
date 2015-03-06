package beakers.service.app.auth

import beakers.domain.system.Role
import beakers.domain.system.RoleGroup
import beakers.domain.system.User
import beakers.errors.domain.user.EmailAlreadyExists
import beakers.errors.domain.UniqueConstraintException
import beakers.errors.domain.user.UsernameAlreadyExists
import beakers.aop.OnUpdateDatabase
import beakers.mail.app.Mailer
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class UserService {

    @Autowired
    Mailer mailer

    @Transactional
    @OnUpdateDatabase
    public User createUser(String username, String password, String email, String fullName, String roleGroup = "user") {
        if (User.findByUsername(username)) {
            throw new UsernameAlreadyExists(username)
        }
        if (User.findByEmail(email)) {
            throw new EmailAlreadyExists(email)
        }

        User user = new User(username: username, email: email, password: password, fullName: fullName, roleGroup: RoleGroup.findByName(roleGroup))
        user.save(flush: true, failOnError: true)

        return user
    }

    @Transactional
    @OnUpdateDatabase
    public RoleGroup createRoleGroup(String name, List<String> roles) {
        if (RoleGroup.findByName(name)) {
            throw new UniqueConstraintException("name", name)
        }

        def list = roles.collect {
            def role = Role.findByName(it)
            if (!role) {
                role = new Role(name: it)
                role.save(flush: true, failOnError: true)
            }
            return role
        }

        RoleGroup group = new RoleGroup(name: name, roles: list)
        group.save(flush: true, failOnError: true)

        return group
    }

    @Transactional
    public User signUpUser(String username, String password, String email, String fullName) {
        def player = createUser(username, password, email, fullName)
        mailer.onSignUp(email, username, password, fullName)
        return player
    }

    @Transactional
    @OnUpdateDatabase
    public User updateUser(User user, String password, String email, String fullName) {
        if (!user) {
            throw new IllegalArgumentException("wrong-args")
        }
        user.fullName = fullName
        user.email = email
        user.password = password
        user.save(flush: true, failOnError: true)
        return user
    }

    @Transactional
    public List listUsers() {
        return User.list()
    }

    public User getCurrentLoggedInUser() {
        def principal = SecurityContextHolder.getContext()?.getAuthentication()?.getPrincipal()
        def username = ""
        if (principal instanceof String) {
            username = principal
        } else {
            username = principal?.username
        }
        if (username) {
            return User.findByUsername(username)
        }
        return null
    }

}
