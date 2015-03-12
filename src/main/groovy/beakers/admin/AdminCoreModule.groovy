package beakers.admin

import beakers.system.BeakersCore
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
public class AdminCoreModule {

    public static void main(String[] args) throws Exception {
        BeakersCore.launch(AdminCoreModule, args)
    }
}
