package beakers.system.interceptors

import beakers.system.controller.AbstractMvcController
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Log4j
@Component
public class MvcInterceptor extends HandlerInterceptorAdapter {

    @Autowired(required = false)
    List<AbstractMvcFilter> filters

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler && handler instanceof HandlerMethod) {
            if (handler.bean && handler.bean instanceof AbstractMvcController) {
                def targetController = ((AbstractMvcController) handler.bean)
                def actionName = handler?.method?.name
                targetController.actionName = actionName
            }
        }

        return filters.every { it.preHandle(request, response, handler) }
    }

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        filters.each { it.postHandle(request, response, handler, modelAndView) }
    }
}