var BUS = {};

// Работа с событиями
BUS.on = function (name, handler) {
    $(document).bind("EVENT_" + name, handler);
};
BUS.trigger = function (name, params) {
    $(document).trigger("EVENT_" + name, [params]);
};
BUS.trigger = function (name, params, extraParams) {
    $(document).trigger("EVENT_" + name, [params, extraParams]);
};
BUS.once = function (name, handler) {
    $(document).one("EVENT_" + name, handler);
};
$(document).ready(function () {
    BUS.trigger("page.ready")
});
