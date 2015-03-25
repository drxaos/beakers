package beakers.auth.events

import beakers.auth.domain.User
import beakers.system.events.Event

class SignUpEvent extends Event {
    User user
}
