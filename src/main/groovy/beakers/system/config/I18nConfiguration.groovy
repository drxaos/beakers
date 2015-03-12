package beakers.system.config

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ReloadableResourceBundleMessageSource

/**
 * Enable messages.properties
 */
@Configuration
public class I18nConfiguration {

    @Bean
    MessageSource messageSource() {
        new ReloadableResourceBundleMessageSource(defaultEncoding: "UTF-8", basenames: ["classpath:messages"])
    }

}