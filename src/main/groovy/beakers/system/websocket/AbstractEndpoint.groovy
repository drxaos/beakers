package beakers.system.websocket

import beakers.system.domain.auth.User
import beakers.system.security.SecurityExpressionEvaluator
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session

class AbstractEndpoint {

    @Autowired
    ApplicationContext applicationContext

    private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>())

    protected boolean closeDisallowedClients = true

    @OnOpen
    final public void onOpenHandler(final Session session) {
        def allow = true
        def securityExpressionEvaluator = applicationContext.getBean(SecurityExpressionEvaluator)
        def auth = this.class.getAnnotation(WebsocketEndpoint)?.auth()
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

    @OnClose
    final public void onCloseHandler(final Session session) {
        if (sessions.remove(session)) {
            User.withTransaction {
                def user = User.findByUsername(session.userPrincipal?.name)
                close(user, session)
            }
        }
    }

    @OnMessage
    final public void onMessageHandler(Session session, String payload) {
        if (sessions.contains(session)) {
            User.withTransaction {
                def user = User.findByUsername(session.userPrincipal?.name)
                receive(user, session, payload)
            }
        }
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
        def securityExpressionEvaluator = applicationContext.getBean(SecurityExpressionEvaluator)
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

    void receive(User user, Session session, String payload) {}

    boolean accept(User user, Session session) { return true }

    void close(User user, Session session) {}

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
