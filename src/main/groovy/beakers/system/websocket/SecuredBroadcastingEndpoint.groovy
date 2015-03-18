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

abstract class SecuredBroadcastingEndpoint {

    @Autowired
    ApplicationContext applicationContext

    private final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>())

    @OnOpen
    public void onOpen(final Session session) {
        // todo check security expression
        sessions.add(session)
    }

    @OnClose
    public void onClose(final Session session) {
        sessions.remove(session)
    }

    @OnMessage
    public void onMessage(Session session, String payload) {
        // todo extract User
        // todo check security expression
        handler(user, session, payload)
    }

    abstract void handler(User user, Session session, String payload);

    public void send(User user, Object message) {
        // todo find session
        // todo send
    }

    public void broadcast(Object message) {
        ObjectMapper mapper = new ObjectMapper()
        Map<String, Object> mappedObject = mapper.convertValue(message, Map.class)
        // customize?
        String json = mapper.writeValueAsString(mappedObject)
        synchronized (sessions) {
            for (Session s : sessions) {
                if (s.isOpen()) {
                    def securityExpressionEvaluator = applicationContext.getBean(SecurityExpressionEvaluator)
                    if (!securityExpressionEvaluator.eval(securityExpression, s.userPrincipal)) {
                        continue
                    }
                    try {
                        s.getAsyncRemote().sendText(json)
                    } catch (IOException ignore) {
                    }
                }
            }
        }
    }

}
