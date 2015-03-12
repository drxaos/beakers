package sample.generic

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GenericSamplesModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GenericSamplesModule, args)
    }
}
