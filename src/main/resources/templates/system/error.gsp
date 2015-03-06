<html>
<head>
    <meta name="layout" content="main"/>
    <title>Error - Beakers</title>
    <sitemesh:parameter name="current" value="error"/>
    <sitemesh:parameter name="username" value="${player?.fullName}"/>
</head>

<body>
<div class="row text-center">
    <div class="page-header">
        <h1>Ошибка</h1>
    </div>

    <p>
        ${url} <br>
        ${exception}
    </p>

    <p>
        На сервере произошла ошибка, попробуйте заново.
    </p>
</div>
</body>
</html>