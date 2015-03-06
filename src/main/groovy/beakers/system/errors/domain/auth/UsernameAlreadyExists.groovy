package beakers.system.errors.domain.auth

import beakers.system.errors.domain.UniqueConstraintException

class UsernameAlreadyExists extends UniqueConstraintException {
    UsernameAlreadyExists(String username) {
        super("username", username)
    }
}
