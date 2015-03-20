<sec:ifLoggedIn>
    <li>
        <a href="/signout" class="navbar-link navbar__logout"
           style="text-decoration: underline">Logout</a>
    </li>
</sec:ifLoggedIn>

<sec:ifNotLoggedIn>
    <li>
        <a href="/login" class="navbar-link navbar__login"
           style="text-decoration: underline">Login</a>
    </li>
</sec:ifNotLoggedIn>
