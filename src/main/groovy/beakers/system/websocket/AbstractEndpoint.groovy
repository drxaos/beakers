package beakers.system.websocket

import beakers.auth.websocket.Endpoint
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

import javax.websocket.Session

abstract class AbstractEndpoint implements Endpoint {

    @Autowired
    ApplicationContext applicationContext

    protected final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>())

    protected boolean closeDisallowedClients = true

    final public void onOpenHandler(final Session session) {
        def allow = true
        allow = allow && accept(session)
        if (allow) {
            sessions.add(session)
        } else if (closeDisallowedClients) {
            session.close()
        }
    }

    final public void onCloseHandler(Session session) {
        if (sessions.remove(session)) {
            close(session)
        }
    }

    final public void onMessageHandler(Session session, String payload) {
        if (sessions.contains(session)) {
            receive(session, payload)
        }
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

    void onError(Session session, Throwable t) {
        error(session, t)
    }

    final public void broadcast(Object message, Closure sessionVerify = { return true }) {
        def json = serializeMessage(message)
        if (json == null) {
            return
        }
        synchronized (sessions) {
            for (Session session : sessions) {
                if (!session.isOpen()) {
                    continue
                }
                if (!sessionVerify.call(session)) {
                    continue
                }
                try {
                    session.asyncRemote.sendText(json)
                } catch (IOException ignore) {
                }
            }
        }
    }

    abstract void receive(Session session, String payload)

    boolean accept(Session session) { return true }

    void close(Session session) {}

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
