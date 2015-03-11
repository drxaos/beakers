package sample.home.controllers

import beakers.system.controller.AbstractMvcController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class HomeController extends AbstractMvcController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String home() {
        return "index/index"
    }

}