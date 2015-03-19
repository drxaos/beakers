
/*
    BUS.trigger("page.alert", {
        alert: "error", // error, warning, info, success, none
        message: "Message HTML",
        reload: true, // reload page and show alert
        fields: [
            {name: "fieldName1", message: "Hint below that field"},
            {name: "fieldName2", message: "Another hint"},
        ]
    });
*/
BUS.on("page.alert", function (event, data) {

    if (data.reload == true) {
        data.reload = false;
        $.jStorage.set("page.alert.url", document.URL);
        $.jStorage.set("page.alert.data", data);
        $.jStorage.setTTL("page.alert.url", 60000);
        $.jStorage.setTTL("page.alert.data", 60000);
        location.reload();
        return;
    }

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

    var easingFadeIn = "easeOutQuart";
    var easingSizeIn = "easeOutCubic";
    var easingFadeOut = "linear";
    var easingSizeOut = "easeOutQuart";

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
            duration: animateHeight ? 200 : 0, easing: easingSizeIn, complete: function () {
                $(".alertsHolder__alert").animate({opacity: 0.87}, {duration: 500, easing: easingFadeIn});
                $(window).trigger('resize');
            }
        });

        $('.alertsHolder__alert').bind('close.bs.alert', function () {
            $(".alertsHolder__alert").animate({opacity: 0.01}, {
                duration: 100, easing: easingFadeOut, complete: function () {
                    $(".alertsHolder__container").html("");

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
                    $('.alertsHolder__container').height(curHeight).animate({height: autoHeight}, {
                        duration: animateHeight ? 200 : 0, easing: easingSizeOut, complete: function () {
                            $(window).trigger('resize');
                        }
                    });

                    if (!animateHeight) {
                        $(document).scrollTop(curScroll + newPageHeight - pageHeight);
                    }
                }
            });
            return false;
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
});

$(window).resize(function () {
    var curWidth = $('.alertsHolder__container').width();
    $('.alertsHolder__alert').css("width", "" + curWidth + "px");
    $('.alertsHolder__container').css('height', 'auto');
    if ($('.alertsHolder__container').html().length > 0) {
        var curHeight = $('.alertsHolder__container').height();
        $('.alertsHolder__container').css("height", "" + (curHeight + 20) + "px");
    }
});

$(document).ready(function () {
    var pageAlertData = $.jStorage.get("page.alert.data");
    if (pageAlertData && $.jStorage.get("page.alert.url") == document.URL) {
        $.jStorage.set("page.alert.url", "");
        BUS.trigger("page.alert", pageAlertData);
    }
});
