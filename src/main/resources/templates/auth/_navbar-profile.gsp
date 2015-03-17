<sec:ifLoggedIn>
    <li <% print pageProperty(name: "page.current") == "profile" ? 'class="active"' : '' %>>
        <a href="/profile">Profile</a>
    </li>
</sec:ifLoggedIn>