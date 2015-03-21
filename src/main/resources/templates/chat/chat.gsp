<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="parent1_link" content="/"/>
    <meta name="parent1_title" content="Home"/>
    <meta name="parent2_link" content="/samples"/>
    <meta name="parent2_title" content="Samples"/>
    <title>Chat</title>
    <sitemesh:parameter name="current" value="chat"/>

    <link rel="stylesheet" href="/static/css/chat.css"/>
    <script src="/static/js/chat.js"></script>

</head>

<body>

<div class="row ">
    <div class="col-md-3">
        <h2>Sources</h2>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/chat/ChatSampleModule.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            ChatSampleModule.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/chat/controller/ChatController.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            ChatController.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/groovy/sample/chat/websocket/ChatEndpoint.groovy">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            ChatEndpoint.groovy
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/resources/templates/chat/chat.gsp">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            chat.gsp
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/resources/static/js/chat.js">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            chat.js
        </a>
        <br/>
        <a href="https://github.com/drxaos/beakers/blob/master/src/main/resources/static/css/chat.css">
            <span class="glyphicon glyphicon-file" aria-hidden="true"></span>
            chat.css
        </a>
        <br/>
    </div>

    <div class="col-md-6">
        <div class="panel panel-default chat__historyPanel">
            <div class="panel-heading">
                RECENT CHAT HISTORY
            </div>

            <div class="panel-body chat__history">
                <ul class="media-list chat__messageList">

                    <li class="media chat__messageTemplate">
                        <div class="media-body">
                            <div class="media">
                                <a class="pull-left" href="#">
                                    <img class="media-object img-circle " src="/static/images/chat/user.png">
                                </a>

                                <div class="media-body">
                                    <span class="chat__messageBody">
                                        Donec sit amet ligula enim. Duis vel condimentum massa.

                                        Donec sit amet ligula enim. Duis vel condimentum massa.Donec sit amet ligula enim.
                                        Duis vel condimentum massa.
                                        Donec sit amet ligula enim. Duis vel condimentum massa.
                                    </span>
                                    <br>
                                    <small class="text-muted chat__messageCaption">Alex Deo | 23rd June at 5:00pm</small>
                                    <hr>
                                </div>
                            </div>
                        </div>
                    </li>

                </ul>
            </div>

            <div class="panel-footer">
                <div class="input-group">
                    <input type="text" class="form-control chat__newMessage" placeholder="Enter Message"
                           disabled="disabled">
                    <span class="input-group-btn">
                        <button class="btn btn-info chat__send" type="button" disabled="disabled">SEND</button>
                    </span>
                </div>
            </div>
        </div>
    </div>

    <div class="col-md-3">
        <div class="panel panel-default chat__usersPanel">
            <div class="panel-heading">
                ONLINE USERS
            </div>

            <div class="panel-body chat__users">
                <ul class="media-list chat__userList">

                    <li class="media chat__userTemplate">
                        <div class="media-body ">
                            <div class="media ">
                                <a class="pull-left" href="#">
                                    <img class="media-object img-circle" style="max-height:40px;"
                                         src="/static/images/chat/user.png">
                                </a>

                                <div class="media-body">
                                    <h5 class="chat__userName" data-user="username">Alex Deo | User</h5>
                                    <small class="text-muted chat__userActive">Online</small>
                                </div>
                            </div>
                        </div>
                    </li>

                </ul>
            </div>

            <div class="panel-footer">
                <div class="input-group">
                    <span class="input-group-btn">
                        <button class="btn btn-danger btn-block chat__status" type="button">RECONNECT</button>
                    </span>
                </div>

            </div>
        </div>

    </div>
</div>

<script>
    <sec:ifExpression value="isAuthenticated()">
    window._chat_enabled = true;
    </sec:ifExpression>
    <sec:ifNotExpression value="isAuthenticated()">
    window._chat_enabled = false; // disable if not logged in
    </sec:ifNotExpression>
</script>
</body>
</html>