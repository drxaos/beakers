package beakers.system.events.auth

import beakers.system.domain.auth.User
import beakers.system.events.Event

class SignUpEvent extends Event {
    User user
}
