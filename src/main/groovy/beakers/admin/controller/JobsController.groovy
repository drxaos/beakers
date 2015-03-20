package beakers.admin.controller

import beakers.jobs.job.JobManager
import beakers.system.controller.AbstractMvcController
import beakers.system.controller.ActionAnswer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

@Controller
@PreAuthorize("hasRole('ROLE_ADMIN')")
class JobsController extends AbstractMvcController {

    @Autowired
    JobManager jobManager

    @RequestMapping(value = "/admin/jobs/list", method = RequestMethod.GET)
    public ModelAndView listJobs() {
        return new ModelAndView("/admin/jobs", [jobs: jobManager.jobs, tasks: jobManager.tasks])
    }

    @RequestMapping(value = "/admin/jobs/exec", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer exec(String id) {
        action {
            def job = jobManager.jobs.find { it.fullName == id }
            def task = jobManager.tasks.find { it.fullName == id }
            if (job) {
                job.runnable.run()
                return success("job-executed")
            } else if (task) {
                task.runnable.run()
                return success("task-executed")
            } else {
                return error("not-found")
            }
        }
    }

}
