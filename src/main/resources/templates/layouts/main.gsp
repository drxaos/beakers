<!DOCTYPE html>
<html>
<head>
    <title><g:layoutTitle default="Home"/> - Beakers</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <link rel="stylesheet" href="/static/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
    %{--<link rel="stylesheet" href="/static/css/bootstrap-theme.min.css"/>--}%
    <link rel="stylesheet" href="/static/css/ripples.min.css">
    <link rel="stylesheet" href="/static/css/material-wfont.min.css">
    <link rel="stylesheet" href="/static/css/beakers.css"/>

    <link href='favicon.ico' rel='icon'/>
    <link rel="icon" type="image/png" href="/static/favicon.png"/>

    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/jquery.form.min.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/bus.js"></script>
    <script src="/static/js/ripples.min.js"></script>
    <script src="/static/js/material.min.js"></script>

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
                        <a class="navbar-brand" href="/"><img src="/static/images/logo-inverse.png" width="34"
                                                              height="34" class="pull-left"/>&nbsp;Beakers</a>
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

            <ul class="breadcrumb" style="margin-bottom: 5px;">
                <g:each in="${["", 1, 2, 3, 4, 5]}" var="n">
                    <g:ifPageProperty name="meta.parent${n}_title">
                        <li><a href="<g:pageProperty name="meta.parent${n}_link" default=""/>"><g:pageProperty
                                name="meta.parent${n}_title" default=""/></a></li>
                    </g:ifPageProperty>
                </g:each>
                <li class="active"><g:layoutTitle default="Home"/></li>
            </ul>

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

<script>
    $(document).ready(function () {
        $.material.init();
    });
</script>

</body>
</html>