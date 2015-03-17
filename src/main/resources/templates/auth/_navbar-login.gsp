<sec:ifLoggedIn>
    <li>
        <a class="navbar-link ellipsis navbar__usernameContainer"
           style="max-width: 35ex; color: #ffffff">
            [<span class="navbar__username"><sec:loggedInUsername/></span>]
        </a>
    </li>
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
