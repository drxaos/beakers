package beakers.system.errors.domain.auth

import beakers.system.errors.domain.ServiceException

class RoleGroupNotExists extends ServiceException {
    RoleGroupNotExists(String name) {
        super("not-exists", [args: [name]])
    }
}
