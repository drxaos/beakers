package beakers.errors.domain.user

import beakers.errors.domain.UniqueConstraintException

class UsernameAlreadyExists extends UniqueConstraintException {
    UsernameAlreadyExists(String username) {
        super("username", username)
    }
}
