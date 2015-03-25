package beakers.auth

import beakers.auth.config.AuthConfig
import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import beakers.system.utils.config.GroovyConfigResource
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class BeakersAuthCoreModule extends BeakersModule {
    @Override
    GroovyConfigResource getConfigResource() {
        return new GroovyConfigResource(AuthConfig)
    }

    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore]
    }
}
