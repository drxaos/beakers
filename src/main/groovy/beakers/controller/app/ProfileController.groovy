package beakers.controller.app

import beakers.controller.system.AbstractMvcController
import beakers.controller.system.ActionAnswer
import beakers.service.app.auth.UserService
import org.codehaus.groovy.grails.validation.Validateable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("hasRole('ROLE_USER')")
public class ProfileController extends AbstractMvcController {
    @Autowired
    UserService userService

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ModelAndView profile() {
        def user = userService.currentLoggedInUser
        return new ModelAndView("profile/profile", [user: user])
    }

    @RequestMapping(value = "/profile/update", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer updateProfile(ProfileParams cmd) {
        action(cmd) {
            def user = userService.currentLoggedInUser
            userService.updateUser(user, cmd.password, cmd.email, cmd.fullName)
            return success("updated")
        }
    }

}

@Validateable
class ProfileParams {
    String password
    String fullName
    String email

    static constraints = {
        password nullable: false, blank: false
        fullName nullable: false, blank: false
        email nullable: false, blank: false
    }
}