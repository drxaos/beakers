<%@ page import="grails.util.Holders" %>
<mail:mail layout="email" subject="Registration at ${Holders.config.app.host}">

    Welcome, ${fullName}
    <p>
        Login: ${username}<br/>
        Password: ${password}<br/>
    </p>

</mail:mail>
