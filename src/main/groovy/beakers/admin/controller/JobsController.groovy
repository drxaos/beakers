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
        return new ModelAndView("/admin/jobs", [tasks: jobManager.tasks])
    }

    @RequestMapping(value = "/admin/jobs/exec", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer exec(String id) {
        action {
            def result = jobManager.execute(id)
            if (!result) {
                return error("not-found")
            }
        }
    }

    @RequestMapping(value = "/admin/jobs/enable", method = RequestMethod.POST)
    @ResponseBody
    public ActionAnswer enable(String id, boolean enable) {
        action {
            def result = jobManager.setEnabled(id, enable)
            if (!result) {
                return error("not-found")
            }
        }
    }

}
