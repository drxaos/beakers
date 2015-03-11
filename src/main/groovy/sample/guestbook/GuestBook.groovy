package sample.guestbook

import beakers.Beakers
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GuestBook {
    public static void main(String[] args) throws Exception {
        Beakers.launch(GuestBook, args)
    }
}
