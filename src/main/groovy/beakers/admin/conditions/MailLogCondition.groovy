package beakers.admin.conditions

import beakers.mail.domain.MailLog
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.springframework.context.annotation.Condition
import org.springframework.context.annotation.ConditionContext
import org.springframework.core.type.AnnotatedTypeMetadata

class MailLogCondition implements Condition {
    @Override
    boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        GrailsApplication grailsApplication = context.beanFactory.getBean("grailsApplication")
        return grailsApplication.getArtefacts("Domain")*.clazz.contains(MailLog)
    }
}
