package net.brylka.BugTrackerJava.issue;

import net.brylka.BugTrackerJava.comment.CommentRepository;
import net.brylka.BugTrackerJava.enums.Priority;
import net.brylka.BugTrackerJava.enums.State;
import net.brylka.BugTrackerJava.enums.Type;
import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.people.PersonRepository;
import net.brylka.BugTrackerJava.project.ProjectRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/issue")
public class IssueController {

    final IssueRepository issueRepository;
    final ProjectRepository projectRepository;
    final PersonRepository personRepository;
    private final CommentRepository commentRepository;

    public IssueController(IssueRepository issueRepository, ProjectRepository projectRepository,
                           PersonRepository personRepository, CommentRepository commentRepository) {
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.personRepository = personRepository;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/")
    ModelAndView index(@ModelAttribute IssueFilter issueFilter) {
        ModelAndView modelAndView = new ModelAndView("issue/index");
        modelAndView.addObject("issues", issueRepository.findAll(issueFilter.buildQuery()));
        modelAndView.addObject("filter", issueFilter);
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("states", State.values());
        modelAndView.addObject("assignees", personRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView createNewIssue() {
        ModelAndView modelAndView = new ModelAndView("issue/create");
        modelAndView.addObject("issue", new Issue());
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("persons", personRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    ModelAndView editIssue(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("issue/create");
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

    @GetMapping("/show/{id}")
    ModelAndView showIssue(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("issue/show");
        modelAndView.addObject("issue", issueRepository.findById(id));
        modelAndView.addObject("projects", projectRepository.findAll());
        modelAndView.addObject("persons", personRepository.findAll());
        return modelAndView;
    }

    @Transactional
    @GetMapping("/delete/{id}")
    ModelAndView deleteIssue(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        commentRepository.deleteByIssueId(id);
        issueRepository.deleteById(id);
        modelAndView.setViewName("redirect:/issue/");
        return modelAndView;
    }

    @PostMapping("/change")
    ResponseEntity<String> change(@RequestParam("id") Long id,
                                  @RequestParam("what") String what,
                                  @RequestParam("change") String change) {
        Issue issue = issueRepository.findById(id).orElseThrow();
        switch (what) {
            case "state" -> issue.setState(State.valueOf(change));
            case "priority" -> issue.setPriority(Priority.valueOf(change));
            case "type" -> issue.setType(Type.valueOf(change));
        }
        issueRepository.save(issue);
        return ResponseEntity.ok().body("OK");
    }
}
