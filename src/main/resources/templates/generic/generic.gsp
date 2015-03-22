<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="parent1_link" content="/"/>
    <meta name="parent1_title" content="Home"/>
    <meta name="parent2_link" content="/samples"/>
    <meta name="parent2_title" content="Samples"/>
    <title>Bus test</title>
    <sitemesh:parameter name="current" value="generic"/>
</head>

<body>

<div class="row">
    <div class="col-md-3">
        <h2>Sources</h2>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/generic/GenericSamplesModule.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            GenericSamplesModule.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/generic/controller/SampleController.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            SampleController.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/generic/events/AnonymousEvent.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            AnonymousEvent.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/generic/events/SampleEvent.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            SampleEvent.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/generic/jobs/Tasks.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            Tasks.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/resources/templates/generic/generic.gsp">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            generic.gsp
        </a>
        <br/>
        <br/>
    </div>

    <div class="col-md-9">

        <button class="btn btn-primary pull-right log__clean" style="margin-right: 10px">Clean</button>

        <div class="well log" style="min-height: 400px">
            <p><b>Bus log:</b></p>
        </div>

    </div>

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