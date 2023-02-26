package net.brylka.BugTrackerJava.project;

import net.brylka.BugTrackerJava.issue.IssueRepository;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectRepository projectRepository;

    private final IssueRepository issueRepository;

    public ProjectController(ProjectService projectService, ProjectRepository projectRepository, IssueRepository issueRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.issueRepository = issueRepository;
    }

    @GetMapping("/")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("project/index");
        modelAndView.addObject("projects", projectRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView createNewProject() {
        ModelAndView modelAndView = new ModelAndView("project/show");
        modelAndView.addObject("project", new Project());
        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView saveProject(@ModelAttribute Project project) {
        ModelAndView modelAndView = new ModelAndView();
        projectService.saveProject(project);
        modelAndView.setViewName("redirect:/project/");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView edit(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("project/show");
        modelAndView.addObject("project", projectRepository.findById(id).orElseThrow());
        return modelAndView;
    }
    @Transactional
    @GetMapping("/delete/{id}")
    @Secured("ROLE_MANAGE_PROJECT")
    ModelAndView delete(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("redirect:/project/");
        issueRepository.deleteByProjectId(id);
        projectRepository.deleteById(id);
        return modelAndView;
    }
}
