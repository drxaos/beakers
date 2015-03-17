var BUS = {};

// Работа с событиями
BUS.on = function (name, handler) {
    $(document).bind("EVENT_" + name, handler);
};
BUS.trigger = function (name, params) {
    $(document).trigger("EVENT_" + name, [params]);
};
BUS.once = function (name, handler) {
    $(document).one("EVENT_" + name, handler);
};
$(document).ready(function () {
    BUS.trigger("page.ready")
});

BUS.on("page.alert", function (event, data) {

    $.jStorage.set("page.alert.url", document.URL);
    $.jStorage.set("page.alert.data", data);
    $.jStorage.setTTL("page.alert.url", 30000);
    $.jStorage.setTTL("page.alert.data", 30000);

    var message = data.message;
    var type = data.alert;

    // fields

    if (data.fields) {
        var selected = false;
        for (var i in data.fields) {
            var f = data.fields[i];
            var $input = $('input[name="' + f.name + '"]');
            var $formGroup = $input.closest(".form-group");
            if ($formGroup.size() > 0) {
                $formGroup.removeClass("has-error")
                    .removeClass("has-success")
                    .removeClass("has-info")
                    .removeClass("has-warning")
                    .removeClass("has-none")
                    .addClass("has-" + type);
                $formGroup.find(".help-block").text(f.message);
                $input.focus().select()
            } else {
                $input
                    .removeClass("has-success")
                    .removeClass("has-info")
                    .removeClass("has-warning")
                    .removeClass("has-none")
                    .addClass("has-" + type);
            }
            $input.unbind("change").change(function () {
                var $input = $(this);
                var $formGroup = $input.closest(".form-group");
                if ($formGroup.size() > 0) {
                    $formGroup.removeClass("has-error")
                        .removeClass("has-success")
                        .removeClass("has-info")
                        .removeClass("has-warning")
                        .removeClass("has-none");
                    $formGroup.find(".help-block").text("");
                } else {
                    $input.removeClass("has-error")
                        .removeClass("has-success")
                        .removeClass("has-info")
                        .removeClass("has-warning")
                        .removeClass("has-none");
                }
            });
        }
    }

    // alert

    if (type == "error") {
        type = "danger"
    }
    if (type == "none") {
        return;
    }

    var pageHeight = $(document).height();
    var curScroll = $(document).scrollTop();
    var curTop = $(".alertsHolder__container").offset().top;
    var animateHeight = curScroll <= curTop + 5;

    var curHeight = $('.alertsHolder__container').height();
    if (curHeight < 1) {
        curHeight = 1;
    }

    $(".alertsHolder__container").html("");
    if (message && type != 'none') {
        message = '<p class="alert__msg">' + message + '</p>';
        $(".alertsHolder__container").html(
            "<div class=\"alert alert-" + type + " alertsHolder__alert alert-dismissible shadow-z-3\" style=\"opacity:0.01; z-index: 999; margin-bottom: 0;\" role=\"alert\">"
            + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\" aria-label=\"Close\"><span aria-hidden=\"true\">&times;</span></button>"
            + message
            + "</div>"
        );
        $('.alertsHolder__container').css('height', 'auto');
        var autoHeight = $('.alertsHolder__container').height();
        var newPageHeight = $(document).height();
        $('.alertsHolder__container').height(curHeight).animate({height: autoHeight + 20}, {
            duration: animateHeight ? 500 : 0, complete: function () {
                $(".alertsHolder__alert").animate({opacity: 0.87}, 200);
            }
        });

        $('.alertsHolder__alert').bind('closed.bs.alert', function () {
            var pageHeight = $(document).height();
            var curScroll = $(document).scrollTop();
            var curTop = $(".alertsHolder__container").offset().top;
            var animateHeight = curScroll <= curTop + 5;

            var curHeight = $('.alertsHolder__container').height();
            if (curHeight < 1) {
                curHeight = 1;
            }
            $('.alertsHolder__container').css('height', 'auto');
            var autoHeight = $('.alertsHolder__container').height();
            var newPageHeight = $(document).height();
            $('.alertsHolder__container').height(curHeight).animate({height: autoHeight}, animateHeight ? 500 : 0);
            $(".alertsHolder__container").html("&nbsp");

            if (!animateHeight) {
                $(document).scrollTop(curScroll + newPageHeight - pageHeight);
            }
        });

        $(".alertsHolder__container .alertsHolder__alert").affix({
            offset: {
                top: function () {
                    return $(".alertsHolder__container").offset().top - 20;
                }
            }
        })
    }
    var curWidth = $('.alertsHolder__container').width();
    $('.alertsHolder__alert').css("width", "" + curWidth + "px");
    $('.alertsHolder__alert').css("top", "20px");

    if (!animateHeight) {
        $(document).scrollTop(curScroll + newPageHeight - pageHeight + 20);
    }

    $(window).unbind("resize").resize(function () {
        var curWidth = $('.alertsHolder__container').width();
        $('.alertsHolder__alert').css("width", "" + curWidth + "px");
        $('.alertsHolder__container').css('height', 'auto');
        var curHeight = $('.alertsHolder__container').height();
        $('.alertsHolder__container').css("height", "" + (curHeight + 20) + "px");
    });
});

$(document).ready(function () {
    var pageAlertData = $.jStorage.get("page.alert.data");
    if (pageAlertData) {
        //BUS.trigger("page.alert", pageAlertData);
    }
});
