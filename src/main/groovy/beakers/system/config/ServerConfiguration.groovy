package beakers.system.config

import beakers.system.utils.web.DatabaseSessionRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer
import org.springframework.boot.context.embedded.ErrorPage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.session.ExpiringSession
import org.springframework.session.SessionRepository
import org.springframework.session.web.http.CookieHttpSessionStrategy
import org.springframework.session.web.http.SessionRepositoryFilter
import org.springframework.web.socket.server.standard.ServerEndpointExporter

import java.util.concurrent.TimeUnit

/**
 * Tomcat configuration
 */
@Configuration
public class ServerConfiguration implements EmbeddedServletContainerCustomizer {

    @Value('${server.port:8080}')
    int port

    @Value('${server.session.timeout:7200}')
    int sessionTimeout

    @Value('${server.session.cache:100}')
    int sessionCacheSize

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        // web sockets
        return new ServerEndpointExporter();
    }


    @Bean
    public SessionRepository<ExpiringSession> sessionRepository() {
        DatabaseSessionRepository repository = new DatabaseSessionRepository(sessionCacheSize);
        repository.setDefaultMaxInactiveInterval(sessionTimeout);
        return repository;
    }

    @Bean
    public <S extends ExpiringSession> SessionRepositoryFilter<? extends ExpiringSession> sessionRepositoryFilter(SessionRepository<S> sessionRepository) {
        SessionRepositoryFilter<S> filter = new SessionRepositoryFilter<>(sessionRepository);
        filter.setHttpSessionStrategy(new CookieHttpSessionStrategy(cookieName: "JSESSION"));
        return filter;
    }

    @Override
    public void customize(ConfigurableEmbeddedServletContainer container) {

        container.setPort(port)
        container.setSessionTimeout(sessionTimeout, TimeUnit.SECONDS)
        container.setErrorPages(new HashSet<ErrorPage>())
        container.addErrorPages(new ErrorPage(HttpStatus.BAD_REQUEST, "/errorRedirect?code=400"))
        container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/errorRedirect?code=403"))
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/errorRedirect?code=404"))
        container.addErrorPages(new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/errorRedirect?code=405"))
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errorRedirect?code=500"))
        container.addErrorPages(new ErrorPage("/errorRedirect"))
        container.mimeMappings.add("ico", "image/x-icon")

    }
}