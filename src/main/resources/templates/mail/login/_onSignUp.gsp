<%@ page import="grails.util.Holders" %>
<g:applyLayout name="email">

    <mail:subject>Registration at ${Holders.config.app.host}</mail:subject>

    Welcome, ${fullName}
    <p>
        Login: ${username}<br/>
        Password: ${password}<br/>
    </p>

</g:applyLayout>