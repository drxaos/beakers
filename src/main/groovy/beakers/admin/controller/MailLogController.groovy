package beakers.admin.controller

import beakers.admin.conditions.MailLogCondition
import org.springframework.context.annotation.Conditional

@Conditional(MailLogCondition)
class MailLogController {
    // TODO
}
