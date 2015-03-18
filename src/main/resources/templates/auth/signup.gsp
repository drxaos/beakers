<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="parent_link" content="/login"/>
    <meta name="parent_title" content="Login"/>
    <title>Registration</title>
</head>

<body>

<div class="row">
    <div class="col-md-3"></div>

    <div class="col-md-6">
        <div class="wrapper well">
            <form class="form-signup text-left" action="/signup/process" method="post">

                <div class="form-group">
                    <h3 class="control-label">Full name</h3>
                    <input type="text" class="form-control" name="fullName" placeholder="Your name" required="" autofocus=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Login</h3>
                    <input type="text" class="form-control" name="username" placeholder="Choose username" required=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">Password</h3>
                    <input type="password" class="form-control" name="password" placeholder="Enter password" required=""/>
                    <span class="help-block"></span>
                </div>

                <div class="form-group">
                    <h3 class="control-label">E-Mail</h3>
                    <input type="text" class="form-control" name="email" placeholder="Your E-Mail" required="" autofocus=""/>
                    <span class="help-block"></span>
                </div>

                <div style="height: 30px">&nbsp;</div>

                <button class="btn btn-lg btn-danger btn-block shadow-z-1" type="submit">Sign up</button>
            </form>
        </div>
    </div>

    <div class="col-md-3"></div>

    <script type="text/javascript">

        $('.form-signup').ajaxForm(function (answer) {
            BUS.trigger("page.alert", answer);
            if (answer.data && answer.data.redirect) {
                window.location = answer.data.redirect;
            }
        });
    </script>
</div>
</body>
</html>