Date.prototype.today = function () {
    return ((this.getDate() < 10) ? "0" : "") + this.getDate() + "/" + (((this.getMonth() + 1) < 10) ? "0" : "") + (this.getMonth() + 1) + "/" + this.getFullYear();
};

// For the time now
Date.prototype.timeNow = function () {
    return ((this.getHours() < 10) ? "0" : "") + this.getHours() + ":" + ((this.getMinutes() < 10) ? "0" : "") + this.getMinutes() + ":" + ((this.getSeconds() < 10) ? "0" : "") + this.getSeconds();
};

BUS.once("page.ready", function () {

    window._chat_ws = BUS.ws("/chat/ws", {
        open: function (e, params) {
            $('.chat__historyPanel,.chat__usersPanel')
                .removeClass('panel-default')
                .removeClass('panel-info')
                .removeClass('panel-danger')
                .addClass('panel-info');
            $('.chat__newMessage, .chat__send').removeAttr("disabled");
        },
        close: function (e, params) {
            $('.chat__historyPanel,.chat__usersPanel')
                .removeClass('panel-default')
                .removeClass('panel-info')
                .removeClass('panel-danger')
                .addClass('panel-default');
            $('.chat__newMessage, .chat__send').attr("disabled", "disabled");
        },
        message: function (e, params) {
            var data = $.parseJSON(params.data);
            if (data.event == "message") {
                var template = $('.chat__messageTemplate').clone(true, true);
                template.removeClass('chat__messageTemplate').addClass('chat__message');
                template.find('.chat__messageBody').text(data.message);
                var newDate = new Date();
                var datetime = newDate.today() + " @ " + newDate.timeNow();
                template.find('.chat__messageCaption').text(data.name + " (" + data.user + ") | " + datetime);
                $('.chat__messageList').append(template);
            } else if (data.event == "connect") {
                var template = $('.chat__userTemplate').clone(true, true);
                template.removeClass('chat__userTemplate').addClass('chat__user');
                template.find('.chat__userName').text(data.name + " (" + data.user + ")").attr("data-username", data.user);
                $('.chat__userList').append(template);
            } else if (data.event == "disconnect") {
                $('.chat__user .chat__userName[data-username=' + data.user + ']').closest('.chat__user').remove();
            }
        },
        error: function (e, params) {
            $('.chat__messageList').append("<p><b>ERROR: " + params.message + "</b></p>");
        }
    });

    $('.chat__status').click(function () {
        window._chat_ws.refresh();
        $('.chat__user').remove();
    });
    $('.chat__send').click(function () {
        var input = $('.chat__newMessage');
        window._chat_ws.send(input.val());
        input.val('');
    });

});

