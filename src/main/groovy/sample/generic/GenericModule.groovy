package sample.generic

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class GenericModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(GenericModule, args)
    }
}
