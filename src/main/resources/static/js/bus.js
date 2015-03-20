var BUS = {};

// Работа с событиями
BUS.on = function (name, handler) {
    $(document).bind("BUS_" + name, handler);
};
BUS.once = function (name, handler) {
    $(document).one("BUS_" + name, handler);
};
BUS.trigger = function (name, params) {
    $(document).trigger("BUS_" + name, [params]);
};
BUS.trigger = function (name, params, extraParams) {
    $(document).trigger("BUS_" + name, [params, extraParams]);
};
$(document).ready(function () {
    BUS.trigger("ready")
});

/*
 Be careful with namespaces!

 > BUS.on("ev1.ns1.ns2", function(){ console.log("callback1") });

 > BUS.on("ev1.ns3", function(){ console.log("callback2") });

 > BUS.on("ev1", function(){ console.log("callback3") });

 > BUS.trigger("ev1", [])
 callback1
 callback2
 callback3

 > BUS.trigger("ev1.ns1", [])
 callback1

 > BUS.trigger("ev1.ns2", [])
 callback1

 > BUS.trigger("ev1.ns3", [])
 callback2

 > BUS.trigger("ev1.ns1.ns2", [])
 callback1

 > BUS.trigger("ev1.ns1.ns3", [])

 > BUS.trigger("ev1.ns2.ns3", [])

 > BUS.trigger("ev1.ns1.ns2.ns3", [])

 */