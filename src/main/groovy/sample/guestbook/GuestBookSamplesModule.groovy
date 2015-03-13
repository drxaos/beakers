package sample.guestbook

import beakers.jobs.BeakersJobsCoreModule
import beakers.mail.BeakersMailCoreModule
import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GuestBookSamplesModule extends BeakersModule {
    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore, BeakersJobsCoreModule, BeakersMailCoreModule]
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GuestBookSamplesModule, args)
    }
}
