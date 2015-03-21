Date.prototype.today = function () {
    return ((this.getDate() < 10) ? "0" : "") + this.getDate() + "/" + (((this.getMonth() + 1) < 10) ? "0" : "") + (this.getMonth() + 1) + "/" + this.getFullYear();
};
Date.prototype.timeNow = function () {
    return ((this.getHours() < 10) ? "0" : "") + this.getHours() + ":" + ((this.getMinutes() < 10) ? "0" : "") + this.getMinutes() + ":" + ((this.getSeconds() < 10) ? "0" : "") + this.getSeconds();
};

// wait for page ready
BUS.once("ready", function () {

    // new reconnecting websocket
    window._chat_ws = BUS.ws("/chat/ws", {
        open: function (e, params) {
            $('.chat__historyPanel,.chat__usersPanel')
                // enable controls
                .removeClass('panel-default')
                .removeClass('panel-info')
                .addClass('panel-info');
            $('.chat__newMessage, .chat__send').removeAttr("disabled");
        },
        close: function (e, params) {
            // disable controls
            $('.chat__historyPanel,.chat__usersPanel')
                .removeClass('panel-default')
                .removeClass('panel-info')
                .addClass('panel-default');
            $('.chat__newMessage, .chat__send').attr("disabled", "disabled");
        },
        message: function (e, params) {
            var data = $.parseJSON(params.data);
            if (data.event == "message") {
                // add message to history
                var template = $('.chat__messageTemplate').clone(true, true);
                template.removeClass('chat__messageTemplate').addClass('chat__message');
                template.find('.chat__messageBody').text(data.message);
                var newDate = new Date();
                var datetime = newDate.today() + " @ " + newDate.timeNow();
                template.find('.chat__messageCaption').text(data.name + " (" + data.user + ") | " + datetime);
                $('.chat__messageList').append(template);
            } else if (data.event == "connect") {
                // add user to contact list
                var template = $('.chat__userTemplate').clone(true, true);
                template.removeClass('chat__userTemplate').addClass('chat__user');
                template.find('.chat__userName').text(data.name + " (" + data.user + ")").attr("data-username", data.user);
                $('.chat__userList').append(template);
            } else if (data.event == "disconnect") {
                // remove user from contact list
                $('.chat__user .chat__userName[data-username=' + data.user + ']').closest('.chat__user').remove();
            }
        },
        error: function (e, params) {
            $('.chat__messageList').append("<p><b>ERROR: " + params.message + "</b></p>");
        },
        ws_idle: !window._chat_enabled // disable if not logged in
    });

    $('.chat__status').click(function () {
        // reconnect websocket
        window._chat_ws.refresh();

        // clean contact list
        $('.chat__user').remove();
    });

    $('.chat__send').click(function () {
        // send messsage to websocket
        var input = $('.chat__newMessage');
        window._chat_ws.send(input.val());
        input.val('');
    });

    if(!window._chat_enabled) {
        // disable if not logged in
        $('.chat__status').attr("disabled", "disabled");
        $('.chat__messageList').append("<p><b>Login, please.</b></p>");
    }

});

