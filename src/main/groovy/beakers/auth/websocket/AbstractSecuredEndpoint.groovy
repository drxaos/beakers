package beakers.auth.websocket

import beakers.auth.domain.User
import beakers.auth.security.SecurityExpressionEvaluator
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

import javax.websocket.Session

abstract class AbstractSecuredEndpoint implements Endpoint {

    @Autowired
    ApplicationContext applicationContext

    @Autowired
    SecurityExpressionEvaluator securityExpressionEvaluator

    private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>())

    protected boolean closeDisallowedClients = true

    final public void onOpenHandler(final Session session) {
        def allow = true
        def auth = this.class.getAnnotation(WebsocketAuth)?.value()
        if (auth) {
            allow = allow && securityExpressionEvaluator.eval(auth, session.userPrincipal)
        }
        User.withTransaction {
            def user = User.findByUsername(session.userPrincipal?.name)
            allow = allow && accept(user, session)
        }
        if (allow) {
            sessions.add(session)
        } else if (closeDisallowedClients) {
            session.close()
        }
    }

    final boolean isConnected(User user) {
        def username = user?.username
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.userPrincipal?.name == username) {
                    return true
                }
            }
        }
        return false
    }

    final Set<User> listConnected() {
        def list = new HashSet<String>()
        synchronized (sessions) {
            for (Session s : sessions) {
                list << s.userPrincipal?.name
            }
        }
        return new HashSet<User>(list.collect { User.findByUsername(it) })
    }

    final User getUser(Session session) {
        return User.findByUsername(session.userPrincipal?.name)
    }

    final public void onCloseHandler(final Session session) {
        if (sessions.remove(session)) {
            User.withTransaction {
                def user = User.findByUsername(session.userPrincipal?.name)
                close(user, session)
            }
        }
    }

    final public void onMessageHandler(Session session, String payload) {
        if (sessions.contains(session)) {
            User.withTransaction {
                def user = User.findByUsername(session.userPrincipal?.name)
                receive(user, session, payload)
            }
        }
    }

    void onError(Session session, Throwable t) {
        error(session, t)
    }

    final public void broadcast(Object message) {
        send(null, null, message)
    }

    final public void broadcast(Object message, String authExpression) {
        send(null, authExpression, message)
    }

    final public void send(Session session, Object message) {
        def json = serializeMessage(message)
        if (json == null) {
            return
        }
        try {
            session.asyncRemote.sendText(json)
        } catch (IOException ignore) {
        }
    }

    final public void send(User user, Object message) {
        send(user, null, message)
    }

    final public void send(User user, String authExpression, Object message) {
        def json = serializeMessage(message)
        if (json == null) {
            return
        }
        def username = user?.username
        synchronized (sessions) {
            for (Session session : sessions) {
                if (!session.isOpen()) {
                    continue
                }
                if (username != null && session.userPrincipal?.name != username) {
                    continue
                }
                if (authExpression != null && !securityExpressionEvaluator.eval(authExpression, session.userPrincipal)) {
                    continue
                }
                try {
                    session.asyncRemote.sendText(json)
                } catch (IOException ignore) {
                }
            }
        }
    }

    abstract void receive(User user, Session session, String payload)

    boolean accept(User user, Session session) { return true }

    void close(User user, Session session) {}

    void error(Session session, Throwable t) {}

    Map toMap(Object message) {
        ObjectMapper mapper = new ObjectMapper()
        if (!(message instanceof Map)) {
            message = mapper.convertValue(message, Map.class)
        }
        return message
    }

    String toJson(Object message) {
        ObjectMapper mapper = new ObjectMapper()
        return mapper.writeValueAsString(message)
    }

    String serializeMessage(Object message) {
        return toJson(message)
    }

}
