package net.brylka.BugTrackerJava.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/project")
public class ProjectController {

    private final ProjectService projectService;

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectService projectService, ProjectRepository projectRepository) {
        this.projectService = projectService;
        this.projectRepository = projectRepository;
    }

    @GetMapping("/")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("project/index");
        modelAndView.addObject("projects", projectRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView createNewProject() {
        ModelAndView modelAndView = new ModelAndView("project/show");
        modelAndView.addObject("project", new Project());
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView saveProject(@ModelAttribute Project project) {
        ModelAndView modelAndView = new ModelAndView();
        projectService.saveProject(project);
        modelAndView.setViewName("redirect:/project/");
        return modelAndView;
    }
}
