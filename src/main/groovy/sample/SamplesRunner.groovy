package sample

import beakers.system.BeakersCore
import beakers.admin.AdminCoreModule
import beakers.jobs.JobsCoreModule
import beakers.mail.MailCoreModule
import beakers.system.config.GroovyConfigResource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import sample.generic.GenericSamplesModule
import sample.guestbook.GuestBookSamplesModule
import sample.home.HomeSamplesModule

@Configuration
public class SamplesRunner {

    @Bean
    GroovyConfigResource samplesConfig() {
        return new GroovyConfigResource(SamplesConfig)
    }

    public static void main(String[] args) throws Exception {
        BeakersCore.launch([
                // core
                BeakersCore,
                MailCoreModule,
                JobsCoreModule,
                AdminCoreModule,

                // modules
                HomeSamplesModule,
                GuestBookSamplesModule,
                GenericSamplesModule,

                // config
                SamplesRunner,
        ], args)
    }
}
