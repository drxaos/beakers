<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome</title>
    <sitemesh:parameter name="current" value="home"/>

    <script src="/static/js/marked.js"></script>

</head>

<body>

<div class="row">
    <div class="col-md-1"></div>

    <div class="col-md-10">

        <div class="well readme__content" style="min-height: 400px">
            ${readme}
        </div>

    </div>

    <div class="col-md-1"></div>
</div>

<script type="text/javascript">

    var $content = $('.readme__content');
    $content.html(marked($content.text()));

</script>
</body>
</html>