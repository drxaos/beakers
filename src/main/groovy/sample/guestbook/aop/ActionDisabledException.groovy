package sample.guestbook.aop

import beakers.system.errors.domain.ServiceException

class ActionDisabledException extends ServiceException{
    ActionDisabledException(String message, Map data) {
        super(message, data)
    }
}
