package sample.generic.events

import beakers.auth.events.websocket.ClientSecuredOutEvent

class AnonymousEvent extends ClientSecuredOutEvent {

    AnonymousEvent() {
        super("isAnonymous()")
    }

    String payload = "Hello anonymous!"
}
