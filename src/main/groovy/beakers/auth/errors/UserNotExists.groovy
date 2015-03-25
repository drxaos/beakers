package beakers.auth.errors

import beakers.system.errors.ServiceException

class UserNotExists extends ServiceException {
    UserNotExists(String username) {
        super("not-exists", [args: [username]])
    }
}
