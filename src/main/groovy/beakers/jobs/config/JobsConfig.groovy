package beakers.jobs.config

environments {
    dev {
        scheduler.enable = false
    }
    prod {
        scheduler.enable = true
    }
    test {
        scheduler.enable = false
    }
}

