package beakers.admin

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class AdminModule {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch(AdminModule, args)
    }
}
