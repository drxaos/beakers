package sample.guestbook

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GuestBookSamplesModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GuestBookSamplesModule, args)
    }
}
