function isFunction(functionToCheck) {
    var getType = {};
    return functionToCheck && getType.toString.call(functionToCheck) === '[object Function]';
}
BUS.ws_counter = 0;
BUS.ws = function (path, handler) {
    var namespace = ".WS" + (BUS.ws_counter++);
    if (isFunction(handler)) {
        $(document).bind("ws_open" + namespace, handler);
        $(document).bind("ws_close" + namespace, handler);
        $(document).bind("ws_message" + namespace, handler);
        $(document).bind("ws_error" + namespace, handler);
    } else {
        if (handler.hasOwnProperty('open') && isFunction(handler.open)) {
            $(document).bind("ws_open" + namespace, handler.open);
        }
        if (handler.hasOwnProperty('close') && isFunction(handler.close)) {
            $(document).bind("ws_close" + namespace, handler.close);
        }
        if (handler.hasOwnProperty('message') && isFunction(handler.message)) {
            $(document).bind("ws_message" + namespace, handler.message);
        }
        if (handler.hasOwnProperty('error') && isFunction(handler.error)) {
            $(document).bind("ws_error" + namespace, handler.error);
        }
    }

    var loc = window.location, wsLocation;
    if (loc.protocol === "https:") {
        wsLocation = "wss:";
    } else {
        wsLocation = "ws:";
    }
    wsLocation += "//" + loc.host;
    wsLocation += path;

    var socket = new ReconnectingWebSocket(wsLocation, null, {
        debug: (window._ws_debug == true),
        reconnectInterval: 1000,
        reconnectDecay: 2,
        maxReconnectInterval: 8000,
        automaticOpen: false
    });

    socket.onopen = function (event) {
        var data = [{wsEventType: "ws_open", wsEvent: event}];
        $(document).trigger("ws_open" + namespace, data);
    };

    socket.onclose = function (event) {
        var data = [{wsEventType: "ws_close", wsEvent: event, wasClean: event.wasClean}];
        $(document).trigger("ws_close" + namespace, data);
    };

    socket.onmessage = function (event) {
        var data = [{wsEventType: "ws_message", wsEvent: event, data: event.data}];
        $(document).trigger("ws_message" + namespace, data);
    };

    socket.onerror = function (error) {
        var data = [{wsEventType: "ws_error", wsEvent: error, message: error.message}];
        $(document).trigger("ws_error" + namespace, data);
    };

    if (!handler.hasOwnProperty('ws_idle') || handler.ws_idle != true) {
        socket.open();
    }
    return socket;
};
