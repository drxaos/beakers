package sample.guestbook

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GuestBookModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GuestBookModule, args)
    }
}
