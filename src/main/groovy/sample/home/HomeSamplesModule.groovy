package sample.home

import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class HomeSamplesModule extends BeakersModule {
    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore]
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(HomeSamplesModule, args)
    }
}
