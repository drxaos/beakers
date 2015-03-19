package beakers.system.events.websocket

import beakers.system.domain.auth.User
import beakers.system.events.Event

import javax.websocket.Session

class ClientInEvent extends Event {
    User user
    Session session
}
