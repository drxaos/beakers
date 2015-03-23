package sample.generic.events

import beakers.system.events.websocket.ClientOutEvent

class AnonymousEvent extends ClientOutEvent {

    AnonymousEvent() {
        super("isAnonymous()")
    }

    String payload = "Hello anonymous!"
}
