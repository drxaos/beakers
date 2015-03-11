package sample.home

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class HomeModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(HomeModule, args)
    }
}
