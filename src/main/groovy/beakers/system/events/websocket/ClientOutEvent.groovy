package beakers.system.events.websocket

import beakers.system.events.Event

class ClientOutEvent extends Event {

    String securityExpression = "hasRole('ROLE_USER')"
}
