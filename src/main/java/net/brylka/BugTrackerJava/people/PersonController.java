package net.brylka.BugTrackerJava.people;

import jakarta.validation.Valid;
import net.brylka.BugTrackerJava.authority.AuthorityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public PersonController(PersonService personService, PersonRepository personRepository, AuthorityRepository authorityRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping("/")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("person", personRepository.findAll());
        modelAndView.addObject("authorities", authorityRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    ModelAndView create() {
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("person", new Person());
        modelAndView.addObject("authorities", personService.findAuthorities());
        return modelAndView;
    }

    @PostMapping("/save")
    ModelAndView createNewUser(@ModelAttribute @Valid Person person) {
        ModelAndView modelAndView = new ModelAndView();
        personService.savePerson(person);
        modelAndView.setViewName("redirect:/user/");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    ModelAndView showEditUserForm(@ModelAttribute @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("people/edit");
        modelAndView.addObject("person", personRepository.findById(id).orElseThrow());
        modelAndView.addObject("authorities", personService.findAuthorities());
        return modelAndView;
    }

    @PostMapping("/edited")
    ModelAndView editing(@RequestParam("id") Long id,
                         @RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("name") String name,
                         @RequestParam("email") String email,
                         @RequestParam("authorities") String authorities) {

        Person person = personRepository.findById(id).orElseThrow();
        person.setName(name);
        person.setUsername(username);
        if (!password.isEmpty()) { person.setPassword(bCryptPasswordEncoder.encode(password)); }
        person.setEmail(email);
        person.setAuthorities(authorityRepository.findAllById(authorities));
        personRepository.save(person);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/user/");
        return modelAndView;
    }
}
