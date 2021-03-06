package sample.chat

import beakers.auth.BeakersAuthCoreModule
import beakers.system.BeakersCore
import beakers.system.types.BeakersModule
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class ChatSampleModule extends BeakersModule {
    @Override
    Class<? extends BeakersModule>[] getDependencies() {
        return [BeakersCore, BeakersAuthCoreModule]
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(ChatSampleModule, args)
    }
}
