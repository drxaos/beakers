package beakers.mail.config

mail.from = "admin@localhost"

environments {
    dev {
        mail.port = 2525
    }
    test {
        mail.port = 2525
    }
    prod {
        mail.port = 25
    }
}
