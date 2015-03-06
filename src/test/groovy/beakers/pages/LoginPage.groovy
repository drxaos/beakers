package beakers.pages

class LoginPage extends MainLayout {
    static url = "/login"
    static at = {
        assert currentUrl ==~ /\/login(\?.*)?/
        loginForm.verifyNotEmpty()
        usernameField.verifyNotEmpty()
        passwordField.verifyNotEmpty()
        submitButton.verifyNotEmpty()
        return true
    }
    static content = {
        heading { $("h1").text() }
        loginForm { $("form.form-signin") }

        usernameField { $("input[name=username]") }
        passwordField { $("input[name=password]") }

        submitButton { $("button.login__submitButton") }
    }
}