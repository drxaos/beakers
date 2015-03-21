<html>
<head>
    <meta name="layout" content="main"/>
    <title>Home</title>
    <sitemesh:parameter name="current" value="home"/>

    <script src="/static/thirdparty/marked/js/marked.js"></script>

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
    var $html = $(marked($content.text()));
    $content.html('');
    $content.append($html);
    $content.find("h2").each(function (i, e) {
        var $h = $(e);
        var $c = $h.nextUntil('h2');
        $h.css("cursor", "pointer");
        $h.prepend($('<i class="mdi-hardware-keyboard-arrow-right"></i>'));
        $c.hide();
        $h.click(function () {
            $c.toggle();
        });
    });

</script>
</body>
</html>