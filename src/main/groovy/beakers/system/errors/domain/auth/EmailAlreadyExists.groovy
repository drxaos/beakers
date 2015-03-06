package beakers.system.errors.domain.auth

import beakers.system.errors.domain.UniqueConstraintException

class EmailAlreadyExists extends UniqueConstraintException {
    EmailAlreadyExists(String email) {
        super("email", email)
    }
}
