<html>
<head>
    <meta name="layout" content="main"/>
    <title>Login</title>
</head>

<body>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <div class="wrapper">
            <form class="form-signin text-left" action="/login/authenticate" method="post">

                <h3>Login</h3>
                <input type="text" class="form-control" name="username" placeholder="Имя" required="" autofocus=""/>

                <h3>Password</h3>
                <input type="password" class="form-control" name="password" placeholder="Пароль" required=""/>

                <div style="margin-bottom: 50px">
                    <a href="/signup" class="pull-right" style="margin: 10px 0; color: red"><b>Регистрация</b></a>
                    <div class="checkbox">
                        <label>
                            <input type="checkbox" value="remember-me" id="rememberMe" name="rememberMe"
                                   checked="checked"> Remember me
                        </label>
                    </div>
                </div>
                <button class="btn btn-lg btn-primary btn-block login__submitButton" type="submit">Log in</button>
            </form>
        </div>
    </div>

    <div class="col-md-3"></div>
</div>
<script>
    var error = ${error};
    if (error == 1) {
        BUS.trigger("page.alert", {alert: "error", message: "Wrong credentials"})
    }
</script>
</body>
</html>