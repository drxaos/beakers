package beakers.auth.errors

import beakers.system.errors.domain.UniqueConstraintException

class EmailAlreadyExists extends UniqueConstraintException {
    EmailAlreadyExists(String email) {
        super("email", email)
    }
}
