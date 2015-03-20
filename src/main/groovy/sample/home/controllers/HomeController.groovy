package sample.home.controllers

import beakers.system.controller.AbstractMvcController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class HomeController extends AbstractMvcController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        String readme = 'https://raw.githubusercontent.com/drxaos/beakers/master/README.md'.toURL().text
        return new ModelAndView("index/index", [readme: readme])
    }

}