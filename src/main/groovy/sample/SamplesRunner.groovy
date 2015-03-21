package sample

import beakers.system.BeakersCore
import beakers.system.utils.config.GroovyConfigResource
import beakers.system.types.BeakersModule
import sample.chat.ChatSampleModule
import sample.generic.GenericSamplesModule
import sample.guestbook.GuestBookSamplesModule
import sample.home.HomeSamplesModule

public class SamplesRunner extends BeakersModule {

    @Override
    GroovyConfigResource getConfigResource() {
        return new GroovyConfigResource(SamplesConfig)
    }

    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [
                HomeSamplesModule,
                GuestBookSamplesModule,
                GenericSamplesModule,
                ChatSampleModule,
        ]
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(SamplesRunner, args)
    }
}
