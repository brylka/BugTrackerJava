package net.brylka.BugTrackerJava.issue;

import jakarta.persistence.EntityManager;
import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.people.PersonRepository;
import net.brylka.BugTrackerJava.project.ProjectRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issue")
public class IssueController {

    final IssueRepository issueRepository;
    final ProjectRepository projectRepository;
    final PersonRepository personRepository;
    final EntityManager entityManager;

    public IssueController(IssueRepository issueRepository, ProjectRepository projectRepository, PersonRepository personRepository, EntityManager entityManager) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.entityManager = entityManager;
    }

    @GetMapping("/")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("issue/index");
        modelAndView.addObject("issues", issueRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView createNewIssue() {
        ModelAndView modelAndView = new ModelAndView("issue/show");
        modelAndView.addObject("issue", new Issue());
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("persons", personRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    ModelAndView editIssue(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("issue/show");
        modelAndView.addObject("issue", issueRepository.findById(id));
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("persons", personRepository.findAll());
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView saveIssue(@ModelAttribute Issue issue) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByUsername(authentication.getName());
        issue.setCreator(person);
        issueRepository.save(issue);
        modelAndView.setViewName("redirect:/issue/");
        return modelAndView;
    }
}
