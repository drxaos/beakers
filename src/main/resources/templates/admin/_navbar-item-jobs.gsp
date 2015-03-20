<sec:ifExpression value="hasRole('ROLE_ADMIN')">
    <li <% print pageProperty(name: "page.current") == "jobs" ? 'class="active"' : '' %>>
        <a href="/admin/jobs/list">Jobs</a>
    </li>
</sec:ifExpression>
