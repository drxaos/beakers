package beakers.system.events.websocket

import beakers.system.events.Event

import javax.websocket.Session

class ClientInEvent extends Event {
    Session session

    String getSessionUsername() {
        return session?.userPrincipal?.name
    }
}
