package sample

server.port = 8080

app.host = "beakers.v-pp.ru"
app.url = "http://${app.host}/"
app.title = "Beakers"
mail.from = "admin@${app.host}"

environments {
    dev {
        datasource.username = "sa"
        datasource.password = "sa"
        datasource.url = "jdbc:h2:file:~/beakers;MODE=MySQL"
        datasource.driver = "org.h2.Driver"
//        datasource.username = "root"
//        datasource.password = "root"
//        datasource.url = "jdbc:mysql://localhost/beakers"
//        datasource.driver = "com.mysql.jdbc.Driver"

//        scheduler.enable = true
    }
}

