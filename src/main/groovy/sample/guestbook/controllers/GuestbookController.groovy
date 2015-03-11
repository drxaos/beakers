package sample.guestbook.controllers

import beakers.system.controller.AbstractMvcController
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@PreAuthorize("isAnonymous() or isAuthenticated()")
public class GuestbookController extends AbstractMvcController {

    @RequestMapping(value = "/guestbook", method = RequestMethod.GET)
    public String guestbook() {
        return "guestbook/guestbook"
    }

}