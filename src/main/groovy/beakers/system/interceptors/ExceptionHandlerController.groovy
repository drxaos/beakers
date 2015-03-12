package beakers.system.interceptors

import beakers.system.domain.auth.User
import beakers.system.service.auth.UserService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Log4j
@ControllerAdvice
public class ExceptionHandlerController {

    @Autowired
    UserService userService

    private RequestCache requestCache = new HttpSessionRequestCache();

    @ExceptionHandler(value = AccessDeniedException.class)
    public String accessDeniedHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            return "redirect:/error?code=403";
        } else {
            requestCache.saveRequest(request, response);
            return "redirect:/login";
        }
    }

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        // If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        log.error(null, e)

        User u = null
        try {
            u = userService.currentLoggedInUser
        } catch (Exception ignore) {
            // nothing
        }

        // Otherwise setup and send the user to a default error-view.
        return "redirect:/error?code=500";
    }
}