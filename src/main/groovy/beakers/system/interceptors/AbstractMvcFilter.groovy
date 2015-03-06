package beakers.system.interceptors

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

abstract class AbstractMvcFilter extends HandlerInterceptorAdapter {

    @Override
    abstract public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception;

    @Override
    abstract public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception;

}
