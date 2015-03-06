package beakers.pages

class HomePage extends MainLayout {
    static url = "/"
    static at = {
        assert currentUrl ==~ /\/(home(\?.*))?/
        return true
    }
    static content = {
        heading { $("h1").text() }
    }
}