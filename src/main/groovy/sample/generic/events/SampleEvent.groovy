package sample.generic.events

import beakers.system.events.websocket.ClientOutEvent

class SampleEvent extends ClientOutEvent {
    String payload
}
