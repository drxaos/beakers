package sample.guestbook.filers

import beakers.system.interceptors.AbstractMvcFilter
import groovy.util.logging.Log4j
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Log4j
@Component
class LoggingFilter extends AbstractMvcFilter {
    @Override
    boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Before controller: " + handler)
        return true
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info("After controller: " + handler)
    }
}
