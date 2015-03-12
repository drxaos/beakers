package sample.home

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class HomeSamplesModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(HomeSamplesModule, args)
    }
}
