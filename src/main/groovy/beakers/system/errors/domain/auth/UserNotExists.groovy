package beakers.system.errors.domain.auth

import beakers.system.errors.domain.ServiceException

class UserNotExists extends ServiceException {
    UserNotExists(String username) {
        super("not-exists", [args: [username]])
    }
}
