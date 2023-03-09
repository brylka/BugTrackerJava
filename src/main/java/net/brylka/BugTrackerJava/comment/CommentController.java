package net.brylka.BugTrackerJava.comment;

import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.people.PersonRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/comment")
public class CommentController {
    private final CommentRepository commentRepository;
    final PersonRepository personRepository;

    public CommentController(CommentRepository commentRepository, PersonRepository personRepository) {
        this.commentRepository = commentRepository;
        this.personRepository = personRepository;
    }

    @GetMapping("/{issue_id}")
    ModelAndView index(@PathVariable("issue_id") Long issue_id) {
        ModelAndView modelAndView = new ModelAndView("comment/index");
        modelAndView.addObject("comments", commentRepository.findByIssueId(issue_id));
        modelAndView.addObject("comment", new Comment());
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView saveComment(@ModelAttribute Comment comment) {
        ModelAndView modelAndView = new ModelAndView();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Person person = personRepository.findByUsername(authentication.getName());
        comment.setAuthor(person);
        commentRepository.save(comment);
        modelAndView.setViewName("redirect:/comment/" + comment.getIssue().getId());
        return modelAndView;
    }

    @PostMapping("/edit")
    ModelAndView editComment(@ModelAttribute Comment comment) {
        ModelAndView modelAndView = new ModelAndView();
        commentRepository.save(comment);
        modelAndView.setViewName("redirect:/comment/" + comment.getIssue().getId());
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    ModelAndView deleteComment(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        Comment comment = commentRepository.findById(Math.toIntExact(id)).orElseThrow();
        commentRepository.delete(comment);
        modelAndView.setViewName("redirect:/comment/" + comment.getIssue().getId());
        return modelAndView;
    }
}
