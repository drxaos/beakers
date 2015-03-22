package beakers.system.config

import beakers.system.BeakersCore
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
        def paths = BeakersCore.activeModules.collect { it.newInstance().messagesPaths }.flatten().unique()
        paths.remove(null)
        new ReloadableResourceBundleMessageSource(defaultEncoding: "UTF-8", basenames: paths)
    }

}