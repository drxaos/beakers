package beakers.admin.controller

import beakers.system.controller.AbstractMvcController
import beakers.system.job.JobManager
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
    @RequestMapping(value = "/admin/jobs/list", method = RequestMethod.GET)
    public ModelAndView listJobs() {
        return new ModelAndView("/admin/jobs", [jobs: jobManager.jobs, tasks: jobManager.tasks])
    }

}
