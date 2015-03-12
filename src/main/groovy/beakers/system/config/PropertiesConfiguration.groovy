package beakers.system.config

import beakers.system.utils.GroovyPlaceholderConfigurer
import org.codehaus.groovy.grails.commons.DefaultGrailsApplication
import org.springframework.beans.BeansException
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.EnvironmentAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment

/**
 * Config loader
 */
@Configuration
class PropertiesConfiguration implements ApplicationContextAware, EnvironmentAware {

    ApplicationContext applicationContext
    Environment environment

    @Bean
    public GroovyPlaceholderConfigurer placeHolderConfigurer() {
        def homeDirectory = applicationContext.getEnvironment().getProperty("user.home")
        def appName = applicationContext.getEnvironment().getProperty("application.name")
        def holders = applicationContext.getBeansOfType(GroovyConfigResource).collect { String name, GroovyConfigResource bean -> bean.resource }

        return new GroovyPlaceholderConfigurer(
                grailsApplication: applicationContext.getBean(DefaultGrailsApplication),
                environment: environment,
                locations: holders + [
                        applicationContext.getResource("classpath:application.class"),
                        applicationContext.getResource("classpath:application.groovy"),
                        applicationContext.getResource("classpath:config/application.class"),
                        applicationContext.getResource("classpath:config/application.groovy"),
                        applicationContext.getResource("file://${homeDirectory}/.config/${appName}.groovy"),
                        applicationContext.getResource("file://${homeDirectory}/.${appName}/config.groovy"),
                        applicationContext.getResource("file://${homeDirectory}/${appName}/config.groovy"),
                ])
    }

    @Override
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext
    }

    @Override
    void setEnvironment(Environment environment) {
        this.environment = environment
    }
}
