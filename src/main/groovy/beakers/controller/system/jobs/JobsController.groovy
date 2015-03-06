package beakers.controller.system.jobs

import beakers.controller.system.AbstractMvcController
import beakers.job.system.JobManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.annotation.Secured
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
class JobsController extends AbstractMvcController {

    @Autowired
    JobManager jobManager

    @Secured(["ROLE_ADMIN"])
    @RequestMapping(value = "/jobs/list", method = RequestMethod.GET)
    public ModelAndView listJobs() {
        return new ModelAndView("system/jobs", [jobs: jobManager.jobs, tasks: jobManager.tasks])
    }

}
