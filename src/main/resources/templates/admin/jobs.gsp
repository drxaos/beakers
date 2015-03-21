<%@ page import="beakers.jobs.utils.JobUtils" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <meta name="parent_link" content="/admin"/>
    <meta name="parent_title" content="Admin"/>
    <title>Jobs list</title>
    <sitemesh:parameter name="current" value="jobs"/>
</head>

<body>

<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <tr class="active">
            <th class="">Name</th>
            <th class="">Type</th>
            <th class="">State</th>
            <th class="">Period</th>
            <th class="">Last start</th>
            <th class="">Last end</th>
            <th class="">Next run</th>
            <th class="">Cron</th>
            <th class=""></th>
        </tr>
        <g:each in="${tasks}" var="task">
            <tr class="">
                <td class="" title="${task.fullName}">
                    ${task.name}<br/>
                    <i>${task.description}</i>
                </td>
                <td class="">${task.type}</td>
                <td class="">${task.inProgress ? "RUNNING" : "IDLE"}</td>
                <td class="">${task.period}</td>
                <td class="">${task.lastStart ?: "None"}</td>
                <td class="">${task.lastEnd ?: "None"}</td>
                <td class="${task.type == "cron" ? "" : "active"}">${task.nextRun}</td>
                <td class="${task.type == "cron" ? "" : "active"}">${task.expression}</td>
                <td class="">
                    <span class="togglebutton togglebutton-material-amber">
                        <label>
                            <input class="jobs__enable"
                                   data-id="${task.fullName}"
                                   type="checkbox"
                                ${task.enabled ? "checked=checked" : ""}>
                        </label>
                    </span>
                    <button class="btn btn-success jobs__run"
                            data-loading-text="<i class='mdi-action-schedule'></i>"
                            data-id="${task.fullName}"
                            style="padding: 0; width: 30px; height: 30px">
                        <i class="mdi-av-play-arrow"></i>
                    </button>
                </td>
            </tr>
        </g:each>
    </table>
</div>
<script>
    $('.jobs__run').click(function () {
        var $btn = $(this).button('loading');

        $.post("/admin/jobs/exec", {id: $(this).attr("data-id")}, function (answer) {
            BUS.trigger("alert", answer);
            $btn.button('reset');
        });
    });
    $('.jobs__enable').change(function (e) {
        $.post("/admin/jobs/enable", {
            id: $(this).attr("data-id"),
            enable: $(this).prop('checked') ? "true" : "false"
        }, function (answer) {
            BUS.trigger("alert", answer);
        });
    });
</script>
</body>
</html>