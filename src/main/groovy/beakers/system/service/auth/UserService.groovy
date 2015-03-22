package beakers.system.service.auth

import beakers.system.aop.OnUpdateDatabase
import beakers.system.domain.auth.Role
import beakers.system.domain.auth.RoleGroup
import beakers.system.domain.auth.User
import beakers.system.errors.domain.UniqueConstraintException
import beakers.system.errors.domain.auth.EmailAlreadyExists
import beakers.system.errors.domain.auth.RoleGroupNotExists
import beakers.system.errors.domain.auth.UserNotExists
import beakers.system.errors.domain.auth.UsernameAlreadyExists
import beakers.system.events.EventBus
import beakers.system.events.auth.SignUpEvent
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Log4j
@Service
public class UserService {

    @Autowired
    EventBus eventBus

    @Transactional
    @OnUpdateDatabase
    public User createUser(String username, String password, String email, String fullName, String roleGroup = "user") {
        if (User.findByUsername(username)) {
            throw new UsernameAlreadyExists(username)
        }
        if (User.findByEmail(email)) {
            throw new EmailAlreadyExists(email)
        }

        def group = RoleGroup.findByName(roleGroup)
        if (!group) {
            group = createRoleGroup(roleGroup, ["ROLE_USER"])
        }
        User user = new User(username: username, email: email, password: password, fullName: fullName, roleGroup: group)
        user.save(flush: true, failOnError: true)

        return user
    }

    @Transactional
    @OnUpdateDatabase
    public User setPassword(String username, String password) {
        def user = User.findByUsername(username)
        if (!user) {
            throw new UserNotExists(username)
        }

        user.setPassword(password)
        user.save(flush: true, failOnError: true)

        return user
    }

    @Transactional
    @OnUpdateDatabase
    public User setRoleGroup(String username, String group) {
        def user = User.findByUsername(username)
        if (!user) {
            throw new UserNotExists(username)
        }
        def rg = RoleGroup.findByName(group)
        if (!rg) {
            throw new RoleGroupNotExists(group)
        }

        user.setRoleGroup(rg)
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
        def user = createUser(username, password, email, fullName)
        eventBus.publish(new SignUpEvent(user: user))
        return user
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
