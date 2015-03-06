package beakers.errors.aop

import beakers.errors.system.ServiceException

class DatabaseUpdatesDisabled extends ServiceException {
    DatabaseUpdatesDisabled() {
        super("please-wait")
    }
}
