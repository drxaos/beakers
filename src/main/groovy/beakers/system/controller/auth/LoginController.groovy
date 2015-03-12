package beakers.system.controller.auth

import beakers.system.controller.AbstractMvcController
import beakers.system.controller.ActionAnswer
import beakers.system.errors.domain.auth.EmailAlreadyExists
import beakers.system.errors.domain.auth.UsernameAlreadyExists
import beakers.system.service.auth.UserService
import beakers.system.utils.SignInUtils
import org.codehaus.groovy.grails.validation.Validateable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class LoginController extends AbstractMvcController {

    @Autowired
    UserService userService

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(
            @RequestParam(value = "error", required = false, defaultValue = "0") String error
    ) {
        return new ModelAndView("auth/login", [error: error])
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public ModelAndView signup() {
        return new ModelAndView("auth/signup", [:])
    }

    @RequestMapping(value = "/signup/process", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer doSignup(SignupParams cmd) {
        action(cmd) {
            def player = userService.signUpUser(cmd.username, cmd.password, cmd.email, cmd.fullName)
            SignInUtils.signin(player.username)
            return answer("registered", [redirect: "/"])
        }
        on(UsernameAlreadyExists) { UsernameAlreadyExists e ->
            return error(e) << field("username", "already-exists")
        }
        on(EmailAlreadyExists) { EmailAlreadyExists e ->
            return error(e) << field("email", "already-exists")
        }
    }

}

@Validateable
class SignupParams {
    String username
    String password
    String fullName
    String email

    static constraints = {
        username nullable: false, blank: false
        password nullable: false, blank: false
        fullName nullable: false, blank: false
        email nullable: false, blank: false
    }
}