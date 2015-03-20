<sec:ifExpression value="hasRole('ROLE_ADMIN')">
    <li <% print pageProperty(name: "page.current") == "users" ? 'class="active"' : '' %>>
        <a href="/admin/users/list">Users</a>
    </li>
</sec:ifExpression>
