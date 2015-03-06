package beakers.taglib

import beakers.service.app.auth.UserService
import grails.gsp.TagLib
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.expression.Expression
import org.springframework.security.access.expression.ExpressionUtils
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.FilterInvocation
import org.springframework.stereotype.Component

import javax.servlet.FilterChain

@TagLib
@Component
class SecurityTagLib {
    static namespace = 'sec'

    @Autowired
    UserService userService

    @Autowired
    SecurityExpressionHandler securityExpressionHandler

    protected Map<String, Expression> expressionCache = [:]
    protected static final FilterChain DUMMY_CHAIN = [
            doFilter: { req, res -> throw new UnsupportedOperationException() }
    ] as FilterChain

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
            Expression expression = findOrCreateExpression(expr)
            FilterInvocation fi = new FilterInvocation(request, response, DUMMY_CHAIN)
            def ctx = securityExpressionHandler.createEvaluationContext(auth, fi)
            return ExpressionUtils.evaluateAsBoolean(expression, ctx)
        }

        return true
    }

    protected synchronized Expression findOrCreateExpression(String text) {
        Expression expression = expressionCache.get(text)
        if (!expression) {
            expression = securityExpressionHandler.expressionParser.parseExpression(text)
            expressionCache[text] = expression
        }
        return expression
    }

}
