package beakers.auth.interceptors

import groovy.util.logging.Log4j
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Log4j
@Order(0)
@ControllerAdvice
public class AuthExceptionHandlerController {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated() && auth.principal != "anonymousUser") {
            return "redirect:/error?code=403";
        } else {
            requestCache.saveRequest(request, response);
            return "redirect:/login";
        }
    }

}