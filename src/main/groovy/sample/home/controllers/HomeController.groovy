package sample.home.controllers

import beakers.system.controller.AbstractMvcController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
public class HomeController extends AbstractMvcController {

    @Autowired
    ApplicationContext applicationContext

    @RequestMapping(value = "/samples", method = RequestMethod.GET)
    public ModelAndView samples() {
        return new ModelAndView("index/samples", [:])
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView home() {
        String readme = applicationContext.getResource("classpath:README.md").inputStream.text
        return new ModelAndView("index/index", [readme: readme])
    }

    @RequestMapping(value = "/docs/{md:.+}", method = RequestMethod.GET)
    public ModelAndView docs(@PathVariable("md") String md) {
        String readme = applicationContext.getResource("classpath:" + md).inputStream.text
        return new ModelAndView("index/index", [readme: readme])
    }

}