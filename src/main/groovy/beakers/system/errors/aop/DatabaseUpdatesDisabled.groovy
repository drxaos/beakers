package beakers.system.errors.aop

import beakers.system.errors.ServiceException

class DatabaseUpdatesDisabled extends ServiceException {
    DatabaseUpdatesDisabled() {
        super("please-wait")
    }
}
