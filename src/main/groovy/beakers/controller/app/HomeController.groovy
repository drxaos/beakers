package beakers.controller.app

import beakers.controller.system.AbstractMvcController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class HomeController extends AbstractMvcController {

    @RequestMapping(value = ["/home", "/"], method = RequestMethod.GET)
    public String home() {
        return "home/home"
    }

}