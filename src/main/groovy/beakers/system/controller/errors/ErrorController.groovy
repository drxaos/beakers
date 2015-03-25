package beakers.system.controller.errors

import beakers.system.controller.AbstractMvcController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.util.UriUtils

import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest
import java.nio.charset.Charset

@Controller
public class ErrorController extends AbstractMvcController {

    @Autowired
    ApplicationContext applicationContext

    @RequestMapping(value = "/errorRedirect", method = RequestMethod.GET)
    public ModelAndView redirectToError(String code, HttpServletRequest request) {
        String from = request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI)
        return new ModelAndView("redirect:/error?code=${code}&from=${UriUtils.encodeFragment(from, Charset.defaultCharset().name())}");
    }

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView showError(String code) {
        if (code == "404") {
            return new ModelAndView("errors/errorNotFound");
        } else if (code == "403") {
            return new ModelAndView("errors/errorAccessDenied");
        }
        return new ModelAndView("errors/error");
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView catcher(HttpServletRequest request) {
        return new ModelAndView("errors/errorNotFound");
    }
}