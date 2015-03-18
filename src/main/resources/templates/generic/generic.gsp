<html>
<head>
    <meta name="layout" content="main"/>
    <title>Generic sample</title>
    <sitemesh:parameter name="current" value="generic"/>

    <script src="/static/js/bus-ws.js"></script>
</head>

<body>

<div class="row">
    <div class="col-md-1"></div>

    <div class="col-md-10">

        <div class="well log">
            <p>Hello!</p>
        </div>

    </div>

    <div class="col-md-1"></div>
</div>

<script>
    BUS.on("ws.connected", function (e) {
        $(".log").append("<p>### Connected ###</p>");
    });
    BUS.on("ws.closed", function (e) {
        $(".log").append("<p>### Closed ###</p>");
    });
    BUS.on("ws.message", function (e, msg, data) {
        $(".log").append("<p>" + data.payload + "</p>");
    });
</script>
</body>
</html>