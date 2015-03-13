package beakers.mail

import beakers.mail.config.MailConfig
import beakers.system.BeakersCore
import beakers.system.config.GroovyConfigResource
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class BeakersMailCoreModule extends BeakersModule {

    @Override
    GroovyConfigResource getConfigResource() {
        return new GroovyConfigResource(MailConfig)
    }

    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore]
    }
}
