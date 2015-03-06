package beakers.controller.system.auth

import beakers.controller.system.AbstractMvcController
import beakers.service.app.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class UsersController extends AbstractMvcController {

    @Autowired
    UserService userService

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView listUsers() {
        def user = userService.currentLoggedInUser
        def list = userService.listUsers()
        return new ModelAndView("system/users", [list: list, current: user])
    }

}