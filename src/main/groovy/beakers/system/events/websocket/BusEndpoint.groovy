package beakers.system.events.websocket

import beakers.system.events.EventBus
import beakers.system.events.EventListener
import beakers.system.websocket.AbstractEndpoint
import beakers.system.websocket.WebsocketEndpoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import javax.websocket.Session

@Component
@WebsocketEndpoint("/bus/ws")
public class BusEndpoint extends AbstractEndpoint {
    @Autowired
    EventBus eventBus

    @EventListener
    void onEvent(ClientOutEvent event) {
        broadcast(event)
    }

    @Override
    void receive(Session session, String payload) {
        eventBus.publish(new ClientInEvent(session: session))
    }

    @Override
    String serializeMessage(Object event) {
        Map mapping = toMap(event)
        mapping.eventType = event.class.simpleName
        mapping.eventTypeQualified = event.class.name
        return toJson(mapping)
    }

}
