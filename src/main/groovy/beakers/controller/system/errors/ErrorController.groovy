package beakers.controller.system.errors

import beakers.controller.system.AbstractMvcController
import beakers.domain.system.User
import beakers.service.app.auth.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class ErrorController extends AbstractMvcController {

    @Autowired
    UserService userService

    @RequestMapping(value = "/fail", method = RequestMethod.GET)
    public ModelAndView showError(String code) {
        User p = null
        try {
            p = userService.currentLoggedInUser
        } catch (Exception ignore) {
            // nothing
        }

        if (code == "404") {
            return new ModelAndView("system/errorNotFound", [player: p]);
        } else if (code == "403") {
            return new ModelAndView("system/errorAccessDenied", [player: p]);
        }

        return new ModelAndView("system/error", [player: p]);
    }
}