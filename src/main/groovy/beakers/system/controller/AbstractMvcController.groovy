package beakers.system.controller

import beakers.system.errors.ServiceException
import beakers.system.errors.domain.ValidationErrors
import beakers.system.service.MessageFactoryService
import groovy.util.logging.Log4j
import org.springframework.beans.factory.annotation.Autowired

@Log4j
abstract class AbstractMvcController {

    public static final String RELOAD = "reload"

    @Autowired
    MessageFactoryService messageFactoryService

    def actionName
    boolean inAction = false
    def actionResult = null
    def actionOutput = null

    final def action(Closure body) {
        action(null, body)
    }

    final def action(def cmd, Closure body) {
        if (inAction) {
            return body()
        }
        actionResult = null
        actionOutput = null
        inAction = true

        try {
            if (cmd != null && !cmd.validate()) {
                actionResult = new ValidationErrors(cmd.errors)
                actionOutput = error(actionResult)
            } else {
                try {
                    actionResult = body()
                    actionOutput = answer(actionResult)
                } catch (Exception e) {
                    log.error("controller exception", e)
                    actionResult = e
                    actionOutput = error(e)
                }
            }
            return actionOutput
        } catch (Exception e) {
            log.error("controller error", e)
        } finally {
            inAction = false
        }
    }

    final def on(Class<? extends Exception> exception, Closure modify) {
        if (exception && actionResult && exception.isAssignableFrom(actionResult.getClass())) {
            return actionOutput = answer(modify(actionResult))
        }
        return actionOutput
    }

    final def answer(obj, data = null) {
        if (obj instanceof ServiceException) {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromException(this.class, actionName, obj, data ?: obj.data)
            return actionOutput
        } else if (obj instanceof Exception) {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromException(this.class, actionName, obj, data)
            return actionOutput
        } else if (obj instanceof ActionAnswer) {
            actionResult = obj.data
            actionOutput = messageFactoryService.updateMessage(obj)
            return actionOutput
        } else {
            actionResult = obj
            actionOutput = messageFactoryService.createAnswerFromCode(this.class, actionName, "none", "" + obj, data)
            return actionOutput
        }
    }

    final def error(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "error")
    }

    final def warning(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "warning")
    }

    final def success(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "success")
    }

    final def info(obj, data = null) {
        answer(obj, data)
        actionOutput = messageFactoryService.updateAnswerType(actionOutput, "info")
    }

    final def field(String fieldName, String errorCode) {
        return messageFactoryService.createFieldError(this.class, actionName, fieldName, errorCode)
    }
}
