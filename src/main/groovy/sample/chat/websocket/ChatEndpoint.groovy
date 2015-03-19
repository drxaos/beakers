package sample.chat.websocket

import beakers.system.domain.auth.User
import beakers.system.websocket.AbstractEndpoint
import beakers.system.websocket.WebsocketEndpoint
import org.springframework.stereotype.Component

import javax.websocket.Session

@Component
@WebsocketEndpoint(value = "/chat/ws", auth = "isAuthenticated()")
public class ChatEndpoint extends AbstractEndpoint {

    @Override
    boolean accept(User user, Session session) {
        if (!isConnected(user)) {
            broadcast([event: "connect", user: user.username, name: user.fullName])
        }
        User.withTransaction {
            (listConnected() << getUser(session)).each {
                send(session, [event: "connect", user: it.username, name: it.fullName])
            }
        }
        return true
    }

    @Override
    void close(User user, Session session) {
        if (!isConnected(user)) {
            broadcast([event: "disconnect", user: user.username])
        }
    }

    @Override
    void receive(User user, Session session, String message) {
        message = message?.trim()
        if (message) {
            broadcast([event: "message", user: user.username, name: user.fullName, message: message])
        }
    }

}
