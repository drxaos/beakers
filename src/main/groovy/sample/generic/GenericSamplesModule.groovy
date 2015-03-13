package sample.generic

import beakers.admin.BeakersAdminCoreModule
import beakers.jobs.BeakersJobsCoreModule
import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GenericSamplesModule extends BeakersModule {
    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore, BeakersJobsCoreModule, BeakersAdminCoreModule]
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GenericSamplesModule, args)
    }
}
