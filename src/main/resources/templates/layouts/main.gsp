<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="Beakers"/></title>

    <script src="/static/js/jquery.min.js"></script>
    <link href="/static/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/css/bootstrap-theme.min.css"/>
    <script src="/static/js/bootstrap.min.js"></script>

    <link href='favicon.ico' rel='icon'/>
    <link rel="icon" type="image/png" href="/static/favicon.png"/>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <link rel="stylesheet" href="/static/css/beakers.css"/>
    <script type="text/javascript" src="/static/js/bus.js"></script>
    <script type="text/javascript" src="/static/js/number-polyfill.js"></script>
    <script type="text/javascript" src="/static/js/jquery.form.min.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/static/js/html5shiv.min.js"></script>
    <![endif]-->

    <g:layoutHead/>
</head>

<body>
<div id="clearfix" class="clearfix">

    <div class="container layout__container">
        <div>
            <nav class="navbar navbar-inverse">
                <div class="container-fluid">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#bs-example-navbar-collapse-1">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="/">Beakers</a>
                    </div>

                    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li <% print pageProperty(name: "page.current") == "home" ? 'class="active"' : '' %>>
                                <a href="/home">Home</a>
                            </li>

                            <sec:ifLoggedIn>
                                <li <% print pageProperty(name: "page.current") == "profile" ? 'class="active"' : '' %>>
                                    <a href="/profile">Profile</a>
                                </li>
                            </sec:ifLoggedIn>

                            <sec:ifExpression value="hasRole('ROLE_ADMIN')">
                                <li <% print pageProperty(name: "page.current") == "jobs" ? 'class="active"' : '' %>>
                                    <a href="/admin/jobs/list">Jobs</a>
                                </li>
                                <li <% print pageProperty(name: "page.current") == "users" ? 'class="active"' : '' %>>
                                    <a href="/admin/users/list">Users</a>
                                </li>
                            </sec:ifExpression>
                        </ul>

                        <ul class="nav navbar-nav navbar-right">
                            <sec:ifLoggedIn>
                                <li>
                                    <a class="navbar-link ellipsis navbar__usernameContainer"
                                       style="max-width: 35ex; color: #ffffff">
                                        [<span class="navbar__username"><sec:loggedInUsername/></span>]
                                    </a>
                                </li>
                                <li>
                                    <a href="/signout" class="navbar-link navbar__logout"
                                       style="text-decoration: underline">Logout</a>
                                </li>
                            </sec:ifLoggedIn>

                            <sec:ifNotLoggedIn>
                                <li>
                                    <a href="/login" class="navbar-link navbar__login"
                                       style="text-decoration: underline">Login</a>
                                </li>
                            </sec:ifNotLoggedIn>
                        </ul>

                    </div>

                </div>
            </nav>

            <div class="alertsHolder__container" style="height: 1px"></div>
            <script language="JavaScript">
                // todo alert from query params
            </script>

            <g:layoutBody/>

            <footer class="footer">
                <div class="container text-center">
                    <a href="http://drxaos.github.io/beakers/" target="_blank">Beakers</a> |
                    <a href="https://github.com/drxaos/beakers" target="_blank">Github</a>
                </div>
            </footer>
        </div>
    </div>

</div>
</body>
</html>