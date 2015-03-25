package beakers.auth.security

import beakers.mail.placeholders.DummyRequest
import beakers.mail.placeholders.DummyResponse
import beakers.mail.placeholders.DummyServletContext
import com.opensymphony.module.sitemesh.RequestConstants
import groovy.util.logging.Log4j
import org.codehaus.groovy.grails.web.pages.GroovyPageBinding
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.sitemesh.GSPSitemeshPage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.expression.Expression
import org.springframework.security.access.expression.ExpressionUtils
import org.springframework.security.access.expression.SecurityExpressionHandler
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.web.FilterInvocation
import org.springframework.stereotype.Component
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.request.RequestContextHolder

import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse

@Log4j
@Component
class SecurityExpressionEvaluator {

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    SecurityExpressionHandler securityExpressionHandler

    protected Map<String, Expression> expressionCache = [:]
    protected static final FilterChain DUMMY_CHAIN = [
            doFilter: { req, res -> throw new UnsupportedOperationException() }
    ] as FilterChain

    public boolean eval(String expr, Authentication auth) {
        def pageScope = new GroovyPageBinding()
        pageScope.setRoot(true)
        def writer = new StringWriter()
        def context = new DummyServletContext()
        context.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext)
        def httpRequest = new DummyRequest(pageScope, context)
        def page = new GSPSitemeshPage()
        httpRequest.setAttribute(RequestConstants.PAGE, page)
        httpRequest.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, applicationContext)
        def httpResponse = new DummyResponse(new PrintWriter(writer))
        def request = new GrailsWebRequest(httpRequest, httpResponse, context)
        RequestContextHolder.setRequestAttributes(request)
        eval(expr, auth, httpRequest, httpResponse)
    }

    public boolean eval(String expr, Authentication auth, ServletRequest request, ServletResponse response) {
        if (auth == null) {
            auth = new AnonymousAuthenticationToken("key", "anonymousUser", AuthorityUtils.createAuthorityList("ROLE_ANONYMOUS"))
        }
        Expression expression = findOrCreateExpression(expr)
        FilterInvocation fi = new FilterInvocation(request, response, DUMMY_CHAIN)
        def ctx = securityExpressionHandler.createEvaluationContext(auth, fi)
        return ExpressionUtils.evaluateAsBoolean(expression, ctx)
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
