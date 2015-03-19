    <li class="dropdown">
        <a href="#" data-target="#" class="dropdown-toggle" data-toggle="dropdown">
            Samples <b class="caret"></b>
        </a>
        <ul class="dropdown-menu">
            <li <% print pageProperty(name: "page.current") == "generic" ? 'class="active"' : '' %>>
                <a href="/generic">Bus test</a>
            </li>
            <li <% print pageProperty(name: "page.current") == "guestbook" ? 'class="active"' : '' %>>
                <a href="/guestbook">Guestbook</a>
            </li>
            <li <% print pageProperty(name: "page.current") == "chat" ? 'class="active"' : '' %>>
                <a href="/chat">Chat</a>
            </li>
        </ul>
    </li>
