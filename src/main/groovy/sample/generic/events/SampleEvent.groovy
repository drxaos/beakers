package sample.generic.events

import beakers.auth.events.websocket.ClientSecuredOutEvent

class SampleEvent extends ClientSecuredOutEvent {
    String payload
}
