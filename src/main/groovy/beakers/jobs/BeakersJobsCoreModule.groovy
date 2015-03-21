package beakers.jobs

import beakers.jobs.config.JobsConfig
import beakers.system.BeakersCore
import beakers.system.config.GroovyConfigResource
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class BeakersJobsCoreModule extends BeakersModule {
    @Override
    GroovyConfigResource getConfigResource() {
        return new GroovyConfigResource(JobsConfig)
    }

    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore]
    }
}
