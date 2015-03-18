var loc = window.location, wsLocation;
if (loc.protocol === "https:") {
    wsLocation = "wss:";
} else {
    wsLocation = "ws:";
}
wsLocation += "//" + loc.host;
wsLocation += "/bus";

var socket = new WebSocket(wsLocation);

socket.onopen = function () {
    socket.send("connected");
    BUS.trigger("ws.connected");
};

socket.onclose = function (event) {
    BUS.trigger("ws.closed", {event: event, wasClean: event.wasClean});
};

socket.onmessage = function (event) {
    BUS.trigger("ws.message", {event: event, data: event.data}, $.parseJSON(event.data));
};

socket.onerror = function (error) {
    alert("Ошибка: " + error.message);
    BUS.trigger("ws.error", {event: error, message: error.message});
};
