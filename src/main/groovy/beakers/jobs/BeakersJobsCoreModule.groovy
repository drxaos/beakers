package beakers.jobs

import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class BeakersJobsCoreModule extends BeakersModule {
    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore]
    }
}
