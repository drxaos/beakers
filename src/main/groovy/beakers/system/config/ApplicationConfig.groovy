package beakers.system.config

grails.views.default.codec = "html" // none, html, base64
grails.views.gsp.encoding = "UTF-8"
grails.converters.encoding = "UTF-8"
//grails.views.gsp.keepgenerateddir = "gsp"

server.port = 8080

app.host = "beakers.example.com"
app.url = "http://${app.host}/"
app.title = "Beakers"

environments {
    dev {
//        datasource.username = "sa"
//        datasource.password = "sa"
//        datasource.url = "jdbc:h2:file:~/beakers;MODE=MySQL"
//        datasource.driver = "org.h2.Driver"
        datasource.username = "root"
        datasource.password = "root"
        datasource.url = "jdbc:mysql://localhost/beakers"
        datasource.driver = "com.mysql.jdbc.Driver"

//        scheduler.enable = true
//        cron.battle = "0 0/5 * * * *"

    }
    test {
        datasource.username = "sa"
        datasource.password = "sa"
        datasource.url = "jdbc:h2:mem:testing;DB_CLOSE_DELAY=-1;MODE=MySQL"
        datasource.driver = "org.h2.Driver"

        // Geb
        driver = {
            String type = System.getenv("webdriver");
            if (type == "firefox") {
                return Class.forName("org.openqa.selenium.firefox.FirefoxDriver").newInstance()
            } else {
                return Class.forName("org.openqa.selenium.chrome.ChromeDriver").newInstance()
            }
        }
        baseUrl = "http://localhost:${server.port}"
        reportsDir = new File("build/geb-reports")
        reportOnTestFailureOnly = true
    }
    prod {
    }
}

