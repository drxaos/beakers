package beakers.system.events

import beakers.system.config.WebsocketConfiguration
import beakers.system.events.websocket.ClientInEvent
import beakers.system.events.websocket.ClientOutEvent
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.websocket.OnClose
import javax.websocket.OnMessage
import javax.websocket.OnOpen
import javax.websocket.Session
import javax.websocket.server.ServerEndpoint

@Component
@ServerEndpoint(value = "/bus", configurator = WebsocketConfiguration.SpringBootConfigurator.class)
public class BusEndpoint {
    @Autowired
    EventBus eventBus

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>())

    @OnOpen
    public void onOpen(final Session session) {
        sessions.add(session)
    }

    @OnClose
    public void onClose(final Session session) {
        sessions.remove(session)
    }

    @OnMessage
    public void onMessage(Session session, String payload) {
        eventBus.publish(new ClientInEvent(
                session: session
        ))
    }

    public void broadcast(Event event) {
        if (!(event instanceof ClientOutEvent)) {
            return
        }

        ObjectMapper mapper = new ObjectMapper()
        Map<String, Object> mappedObject = mapper.convertValue(event, Map.class)
        mappedObject.eventType = event.class.simpleName
        mappedObject.eventTypeQualified = event.class.name
        String json = mapper.writeValueAsString(mappedObject)
        synchronized (sessions) {
            for (Session s : sessions) {
                // todo check event.securityExpression
                if (s.isOpen()) {
                    try {
                        s.getBasicRemote().sendText(json)
                    } catch (IOException ignore) {
                    }
                }
            }
        }
    }

}
