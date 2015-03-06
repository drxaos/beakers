package beakers.errors.domain.user

import beakers.errors.domain.UniqueConstraintException

class EmailAlreadyExists extends UniqueConstraintException {
    EmailAlreadyExists(String email) {
        super("email", email)
    }
}
