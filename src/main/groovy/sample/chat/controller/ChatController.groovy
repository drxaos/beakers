package sample.chat.controller

import beakers.system.controller.AbstractMvcController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class ChatController extends AbstractMvcController {

    @RequestMapping(value = "/chat", method = RequestMethod.GET)
    public ModelAndView chat() {
        return new ModelAndView("chat/chat", [:])
    }
}
