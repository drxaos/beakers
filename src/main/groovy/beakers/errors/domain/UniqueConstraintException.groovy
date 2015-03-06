package beakers.errors.domain

import beakers.errors.system.ServiceException

class UniqueConstraintException extends ServiceException {
    UniqueConstraintException(String name, String value) {
        super("${name}-exists", [args: [value]])
    }
}
