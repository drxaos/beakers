package beakers.system.errors.aop

import beakers.system.errors.domain.ServiceException

class DatabaseUpdatesDisabled extends ServiceException {
    DatabaseUpdatesDisabled() {
        super("please-wait")
    }
}
