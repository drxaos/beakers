package beakers.system.taglib

import beakers.system.security.SecurityExpressionEvaluator
import beakers.system.service.auth.UserService
import grails.gsp.TagLib
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@TagLib
@Component
class SecurityTagLib {
    static namespace = 'sec'

    @Autowired
    UserService userService

    @Autowired
    SecurityExpressionEvaluator securityExpressionEvaluator

    def ifLoggedIn = { attrs, body ->
        if (userService.currentLoggedInUser) {
            out << body()
        }
    }

    def ifExpression = { attrs, body ->
        if (evaluate(attrs.remove("value"))) {
            out << body()
        }
    }

    def ifNotExpression = { attrs, body ->
        if (!evaluate(attrs.remove("value"))) {
            out << body()
        }
    }

    def ifNotLoggedIn = { attrs, body ->
        if (!userService.currentLoggedInUser) {
            out << body()
        }
    }

    def loggedInUsername = { attrs, body ->
        out << userService.currentLoggedInUser.fullName
    }

    protected boolean evaluate(String expr) {
        def auth = SecurityContextHolder.getContext().getAuthentication()

        if (expr) {
            return securityExpressionEvaluator.eval(expr, auth, request, response)
        }

        return false
    }


}
