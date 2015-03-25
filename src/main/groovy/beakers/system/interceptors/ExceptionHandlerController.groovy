package beakers.system.interceptors

import groovy.util.logging.Log4j
import org.springframework.core.Ordered
import org.springframework.core.annotation.AnnotationUtils
import org.springframework.core.annotation.Order
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

import javax.servlet.http.HttpServletRequest

@Log4j
@Order(Ordered.LOWEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(value = Exception.class)
    public String defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {

        // If the exception is annotated with @ResponseStatus rethrow it and let the framework handle it
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        }

        log.error(null, e)

        // Otherwise setup and send the user to a default error-view.
        return "redirect:/error?code=500";
    }
}