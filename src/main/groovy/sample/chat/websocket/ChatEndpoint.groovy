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
            // first connection of this user
            broadcast([event: "connect", user: user.username, name: user.fullName])
        }
        User.withTransaction {
            // send all users to contact list
            (listConnected() << getUser(session)).each {
                send(session, [event: "connect", user: it.username, name: it.fullName])
            }
        }
        // save connection
        return true
    }

    @Override
    void close(User user, Session session) {
        if (!isConnected(user)) {
            // it was last connection of the user
            broadcast([event: "disconnect", user: user.username])
        }
    }

    @Override
    void receive(User user, Session session, String message) {
        message = message?.trim()
        if (message) {
            // send message to all saved connections
            broadcast([event: "message", user: user.username, name: user.fullName, message: message])
        }
    }

}
