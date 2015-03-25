package beakers.system.errors.domain

import beakers.system.errors.ServiceException

class UniqueConstraintException extends ServiceException {
    UniqueConstraintException(String name, String value) {
        super("${name}-exists", [args: [value]])
    }
}
