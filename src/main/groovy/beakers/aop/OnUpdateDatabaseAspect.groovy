package beakers.aop

import beakers.errors.aop.DatabaseUpdatesDisabled
import beakers.service.system.SystemService
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Aspect
@Component
class OnUpdateDatabaseAspect {

    @Autowired
    SystemService systemService

    @Around("@annotation(disableIfBattleInProgress)")
    public Object checkAccess(ProceedingJoinPoint pjp, OnUpdateDatabase disableIfBattleInProgress) throws Throwable {
        if (systemService.parameter("maintenance", "0") == "1") {
            throw new DatabaseUpdatesDisabled()
        }
        return pjp.proceed()
    }

}