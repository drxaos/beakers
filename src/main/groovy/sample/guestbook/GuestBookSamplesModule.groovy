package sample.guestbook

import beakers.system.BeakersCore
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
public class GuestBookSamplesModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GuestBookSamplesModule, args)
    }
}
