package sample.generic.controller

import beakers.system.controller.AbstractMvcController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class SampleController extends AbstractMvcController {

    @RequestMapping(value = "/generic", method = RequestMethod.GET)
    public String generic() {
        return "generic/generic"
    }

}