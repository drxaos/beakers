package sample.home

import beakers.system.BeakersCore
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
public class HomeSamplesModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(HomeSamplesModule, args)
    }
}
