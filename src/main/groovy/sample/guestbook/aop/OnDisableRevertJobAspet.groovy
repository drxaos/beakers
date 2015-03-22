package sample.guestbook.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component

@Aspect
@Component
class OnDisableRevertJobAspet {

    @Around("execution(boolean beakers.jobs.job.JobManager.setEnabled(String,boolean)) && args(name,enabled)")
    public Object preventCleanerDisabling(ProceedingJoinPoint pjp, String name, boolean enabled) throws Throwable {
        if (name.contains("RevertJob.execute()") && !enabled) {
            throw new ActionDisabledException("cannot-disable-job", [:])
        }
        return pjp.proceed()
    }

}