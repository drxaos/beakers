package beakers.auth.events.websocket

import beakers.auth.security.SecurityExpressionEvaluator
import beakers.system.events.EventBus
import beakers.system.events.EventListener
import beakers.system.events.websocket.BusEndpoint
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
public class SecuredEventSender {

    @Autowired
    BusEndpoint busEndpoint

    @Autowired
    EventBus eventBus

    @Autowired
    SecurityExpressionEvaluator securityExpressionEvaluator

    @EventListener
    void onEvent(ClientSecuredOutEvent event) {
        busEndpoint.broadcast(event, { session ->
            if (event.securityExpression != null &&
                    !securityExpressionEvaluator.eval(event.securityExpression, session.userPrincipal)) {
                return false
            }
            return true
        })
    }
}
