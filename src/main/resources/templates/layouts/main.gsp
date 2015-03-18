<%@ page import="grails.util.Holders" %>
<!DOCTYPE html>
<html>
<head>
    %{--TITLE--}%
    <title><g:layoutTitle default="Home"/> - ${Holders.config.app.title}</title>

    %{--META--}%
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    %{--CSS--}%
    <link rel="stylesheet" href="/static/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/css/ripples.min.css">
    <link rel="stylesheet" href="/static/css/material-wfont.min.css">
    <link rel="stylesheet" href="/static/css/octicons.css">
    <link rel="stylesheet" href="/static/css/beakers.css"/>

    %{--FAVICON--}%
    <link href='favicon.ico' rel='icon'/>
    <link rel="icon" type="image/png" href="/static/favicon.png"/>

    %{--JS--}%
    <script src="/static/js/json2.min.js"></script>
    <script src="/static/js/jquery.min.js"></script>
    <script src="/static/js/jquery-ui.min.js"></script>
    <script src="/static/js/jquery.form.min.js"></script>
    <script src="/static/js/jstorage.js"></script>
    <script src="/static/js/bootstrap.min.js"></script>
    <script src="/static/js/ripples.min.js"></script>
    <script src="/static/js/material.min.js"></script>
    <script src="/static/js/bus.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/static/js/html5shiv.min.js"></script>
    <![endif]-->

    <g:layoutHead/>
</head>

<body>

<div id="clearfix" class="clearfix">

    <nav class="navbar navbar-inverse shadow-z-2">
        <div class="container container-fluid">

            <g:render template="layouts/header"/>

            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <g:render template="index/navbar-home"/>
                    <g:render template="auth/navbar-profile"/>
                    <g:render template="admin/navbar-menu"/>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <g:render template="auth/navbar-login"/>
                </ul>
            </div>

        </div>
    </nav>

    <div class="container layout__container">

        <g:render template="layouts/alert"/>

        <g:render template="layouts/breadcrumb"/>

        <div class="z-1">
            <g:layoutBody/>
        </div>

        <g:render template="layouts/footer"/>
    </div>

</div>

<script>
    $(document).ready(function () {
        $.material.init();
    });
</script>

</body>
</html>