

import beakers.BeakersCore
import beakers.admin.AdminModule
import sample.generic.GenericModule
import sample.guestbook.GuestBookModule
import sample.home.HomeModule

public class SamplesRunner {
    public static void main(String[] args) throws Exception {
        BeakersCore.launch([
                AdminModule,
                HomeModule,
                GuestBookModule,
                GenericModule,
        ], args)
    }
}
