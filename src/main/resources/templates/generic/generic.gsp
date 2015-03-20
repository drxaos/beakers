<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="parent_link" content="/samples"/>
    <meta name="parent_title" content="Samples"/>
    <title>Bus test</title>
    <sitemesh:parameter name="current" value="generic"/>
</head>

<body>

<div class="row">
    <div class="col-md-1"></div>

    <div class="col-md-10">

        <button class="btn btn-primary pull-right log__clean" style="margin-right: 10px">Clean</button>

        <div class="well log" style="min-height: 400px">
            <p><b>Bus log:</b></p>
        </div>

    </div>

    <div class="col-md-1"></div>
</div>

<script>
    BUS.ws("/bus/ws", {
                open: function (e, params) {
                    $(".log").append("<p>### Connected ###<br/><i><b>" + params.wsEventType + "</b></i></p>");
                },
                close: function (e, params) {
                    $(".log").append("<p>### Closed ###<br/><i><b>" + params.wsEventType + "</b></i></p>");
                },
                message: function (e, params) {
                    var data = $.parseJSON(params.data);
                    $(".log").append("<p>" + data.payload + "<br/><i><b>" + params.wsEventType + "</b>: " + params.data + "</i></p>");
                },
                error: function (e, params) {
                    $(".log").append("<p>ERROR: " + params.message + "<br/><i><b>" + params.wsEventType + "</b></i></p>");
                }
            }
    );
    $('.log__clean').click(function () {
        $('.log').html('<p><b>Bus log:</b></p>');
        BUS.trigger("alert", {alert: "info", message: "Log truncated"});
    });
</script>
</body>
</html>