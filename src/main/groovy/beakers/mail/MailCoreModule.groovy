package beakers.mail

import beakers.mail.config.MailConfig
import beakers.system.BeakersCore
import beakers.system.config.GroovyConfigResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
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
