package beakers.auth.events.websocket

import beakers.system.events.Event
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(["securityExpression"])
class ClientSecuredOutEvent extends Event {

    String securityExpression = "hasRole('ROLE_USER')"

    ClientSecuredOutEvent(String securityExpression) {
        this.securityExpression = securityExpression
    }

    ClientSecuredOutEvent() {
    }
}
