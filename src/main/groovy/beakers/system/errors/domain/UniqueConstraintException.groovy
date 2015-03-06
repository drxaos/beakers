package beakers.system.errors.domain

class UniqueConstraintException extends ServiceException {
    UniqueConstraintException(String name, String value) {
        super("${name}-exists", [args: [value]])
    }
}
