package sample

import beakers.Beakers
import sample.guestbook.GuestBook
import sample.home.Home

public class Samples {
    public static void main(String[] args) throws Exception {
        Beakers.launch([
                Home,
                GuestBook,
        ], args)
    }
}
