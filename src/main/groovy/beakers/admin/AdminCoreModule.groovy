package beakers.admin

import beakers.BeakersCore
import org.springframework.context.annotation.ComponentScan

@ComponentScan
public class AdminCoreModule {

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(AdminCoreModule, args)
    }
}
