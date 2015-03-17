<sec:ifExpression value="hasRole('ROLE_ADMIN')">
    <li class="dropdown">
        <a href="#" data-target="#" class="dropdown-toggle" data-toggle="dropdown">
            Administration <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <li <% print pageProperty(name: "page.current") == "jobs" ? 'class="active"' : '' %>>
                <a href="/admin/jobs/list">Jobs</a>
            </li>
            <li <% print pageProperty(name: "page.current") == "users" ? 'class="active"' : '' %>>
                <a href="/admin/users/list">Users</a>
            </li>
        </ul>
    </li>
</sec:ifExpression>
