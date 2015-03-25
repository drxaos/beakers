package beakers.auth.errors

import beakers.system.errors.ServiceException

class RoleGroupNotExists extends ServiceException {
    RoleGroupNotExists(String name) {
        super("not-exists", [args: [name]])
    }
}
