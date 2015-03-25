package beakers.admin

import beakers.auth.BeakersAuthCoreModule
import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class BeakersAdminCoreModule extends BeakersModule {
    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore, BeakersAuthCoreModule]
    }
}
