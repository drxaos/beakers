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
    <link rel="stylesheet" href="/static/thirdparty/font-awesome/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="/static/thirdparty/bootstrap/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="/static/thirdparty/material/css/ripples.min.css">
    <link rel="stylesheet" href="/static/thirdparty/material/css/material-fullpalette.min.css">
    <link rel="stylesheet" href="/static/thirdparty/octicons/css/octicons.css">
    <link rel="stylesheet" href="/static/css/beakers.css"/>

    %{--FAVICON--}%
    <link href='favicon.ico' rel='icon'/>
    <link rel="icon" type="image/png" href="/static/favicon.png"/>

    %{--JS--}%
    <script src="/static/thirdparty/jquery/js/json2.min.js"></script>
    <script src="/static/thirdparty/jquery/js/jquery.min.js"></script>
    <script src="/static/thirdparty/jquery/js/jquery-ui.min.js"></script>
    <script src="/static/thirdparty/jquery/js/jquery.form.min.js"></script>
    <script src="/static/thirdparty/jquery/js/jstorage.js"></script>
    <script src="/static/thirdparty/bootstrap/js/bootstrap.min.js"></script>
    <script src="/static/thirdparty/material/js/ripples.min.js"></script>
    <script src="/static/thirdparty/material/js/material.min.js"></script>
    <script src="/static/thirdparty/reconnecting-websocket/js/reconnecting-websocket.min.js"></script>
    <script src="/static/js/bus.js"></script>
    <script src="/static/js/bus-alert.js"></script>
    <script src="/static/js/bus-ws.js"></script>

    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="/static/thirdparty/html5shiv/js/html5shiv.min.js"></script>
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
                    <g:render template="index/navbar-item-home"/>

                    <g:render template="samples/navbar-dropdown">
                        <g:render template="generic/navbar-item-generic"/>
                        <g:render template="guestbook/navbar-item-guestbook"/>
                        <g:render template="chat/navbar-item-chat"/>
                    </g:render>

                    <g:render template="admin/navbar-dropdown">
                        <g:render template="admin/navbar-item-jobs"/>
                        <g:render template="admin/navbar-item-users"/>
                    </g:render>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <g:render template="auth/navbar-item-username"/>
                    <g:render template="auth/navbar-item-profile"/>
                    <g:render template="auth/navbar-item-login"/>
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
    BUS.once("ready", function () {
        $.material.init();
    });
</script>

</body>
</html>