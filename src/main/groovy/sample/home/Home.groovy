package sample.home

import beakers.Beakers
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class Home {
    public static void main(String[] args) throws Exception {
        Beakers.launch(Home, args)
    }
}
