<sec:ifExpression value="hasRole('ROLE_ADMIN')">
    <li class="dropdown">
        <a href="#" data-target="#" class="dropdown-toggle" data-toggle="dropdown">
            Administration <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <%=body()%>
        </ul>
    </li>
</sec:ifExpression>
