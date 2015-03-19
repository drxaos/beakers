package beakers.system.events.websocket

import beakers.system.domain.auth.User
import beakers.system.events.EventBus
import beakers.system.websocket.AbstractEndpoint
import beakers.system.websocket.WebsocketEndpoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.websocket.Session

@Component
@WebsocketEndpoint(value = "/bus/ws", auth = "isAnonymous() or isAuthenticated()")
public class BusEndpoint extends AbstractEndpoint {
    @Autowired
    EventBus eventBus

    @Override
    void receive(User user, Session session, String payload) {
        eventBus.publish(new ClientInEvent(
                user: user,
                session: session
        ))
    }

    @Override
    String serializeMessage(Object event) {
        Map mapping = toMap(event)
        mapping.eventType = event.class.simpleName
        mapping.eventTypeQualified = event.class.name
        mapping.remove("securityExpression")
        return toJson(mapping)
    }

}
