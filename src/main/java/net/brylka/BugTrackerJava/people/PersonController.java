package net.brylka.BugTrackerJava.people;

import jakarta.validation.Valid;
import net.brylka.BugTrackerJava.authority.AuthorityRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class PersonController {

    private final PersonService personService;
    private final PersonRepository personRepository;
    private final AuthorityRepository authorityRepository;

    public PersonController(PersonService personService, PersonRepository personRepository, AuthorityRepository authorityRepository) {
        this.personService = personService;
        this.personRepository = personRepository;
        this.authorityRepository = authorityRepository;
    }

    @RequestMapping("/")
    @Secured("ROLE_USER_TAB")
    ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("people/index");
        modelAndView.addObject("person", personRepository.findAll());
        modelAndView.addObject("authorities", authorityRepository.findAll());
        return modelAndView;
    }

    @GetMapping("/create")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView showNewUserForm() {
        ModelAndView modelAndView = new ModelAndView("people/create");
        modelAndView.addObject("person", new Person());
        modelAndView.addObject("authorities", personService.findAuthorities());
        return modelAndView;
    }

    @PostMapping("/save")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView createNewUser(@ModelAttribute @Valid Person person) {
        ModelAndView modelAndView = new ModelAndView();
        personService.savePersonAccount(person);
        modelAndView.setViewName("redirect:/user/");
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView showEditUserForm(@ModelAttribute @PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("people/edit");
        modelAndView.addObject("person", personRepository.findById(id).orElseThrow());
        modelAndView.addObject("authorities", personService.findAuthorities());
        return modelAndView;
    }

    @PostMapping("/edited")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView editUser(@ModelAttribute @Valid Person person) {
        ModelAndView modelAndView = new ModelAndView();
        if (person.getPassword().isEmpty()) {
            Person existingPerson = personService.findById(person.getId());
            person.setPassword(existingPerson.getPassword());
            personService.savePersonAccountWithoutHash(person);
        } else {
            personService.savePersonAccount(person);
        }
        modelAndView.setViewName("redirect:/user/");
        return modelAndView;
    }

    @GetMapping("/delete/{id}")
    @Secured("ROLE_MANAGE_USERS")
    ModelAndView deleteUser(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        personService.markPersonAsDeleted(id);
        modelAndView.setViewName("redirect:/user/");
        return modelAndView;
    }

    @PostMapping("/enable")
    @Secured("ROLE_MANAGE_USERS")
    ResponseEntity<Void> updateEnabled(@RequestParam("id") Long id) {
        Person person = personRepository.findById(id).orElseThrow();
        person.setEnabled(!person.getEnabled());
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/account")
    ModelAndView showUserAccount() {
        ModelAndView modelAndView = new ModelAndView("people/account");
        String id = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = personRepository.findByUsername(id);
        modelAndView.addObject("person", personService.editPerson(person.getId()));
        return modelAndView;
    }

    @PostMapping("/account")
    ModelAndView editUserAccount(@ModelAttribute @Valid PersonAccount personAccount) {
        ModelAndView modelAndView = new ModelAndView();
        personService.savePersonAccount(personAccount);
        modelAndView.setViewName("redirect:/user/account");
        return modelAndView;
    }

    @GetMapping("/password")
    ModelAndView showUserPassword() {
        ModelAndView modelAndView = new ModelAndView("people/password");
        String id = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        Person person = personRepository.findByUsername(id);
        modelAndView.addObject("personPassword", new PersonPassword());
        modelAndView.addObject("id", person.getId());
        return modelAndView;
    }

    @PostMapping("/password")
    ModelAndView editUserPassword(@ModelAttribute @Valid PersonPassword personPassword) {
        ModelAndView modelAndView = new ModelAndView();
        personService.savePersonPassword(personPassword);
        modelAndView.setViewName("redirect:/user/password");
        return modelAndView;
    }
}
