package beakers.mail

import beakers.BeakersCore
import beakers.mail.config.MailConfig
import beakers.system.config.GroovyConfigResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class MailCoreModule {
    @Bean
    GroovyConfigResource mailCoreModuleConfig() {
        return new GroovyConfigResource(MailConfig)
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(MailCoreModule, args)
    }
}
