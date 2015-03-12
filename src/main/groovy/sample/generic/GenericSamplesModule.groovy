package sample.generic

import beakers.system.BeakersCore
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
public class GenericSamplesModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GenericSamplesModule, args)
    }
}