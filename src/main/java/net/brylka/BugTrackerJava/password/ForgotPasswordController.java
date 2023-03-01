package net.brylka.BugTrackerJava.password;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.brylka.BugTrackerJava.mail.Mail;
import net.brylka.BugTrackerJava.mail.MailService;
import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.people.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
@RequestMapping("/forgot-password")
public class ForgotPasswordController {

    private final PersonService personService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    final private MailService mailService;

    public ForgotPasswordController(PersonService personService,
                                    PasswordResetTokenRepository passwordResetTokenRepository, MailService mailService) {
        this.personService = personService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailService = mailService;
    }

    @GetMapping
    ModelAndView viewPage() {
        ModelAndView modelAndView = new ModelAndView("password/forgot-password");
        modelAndView.addObject("passwordForgot", new PasswordForgot());
        return modelAndView;
    }

    @PostMapping
    public String processPasswordForgot(@Valid @ModelAttribute("passwordForgot") PasswordForgot passwordForgot,
                                        BindingResult result,
                                        Model model,
                                        RedirectAttributes attributes,
                                        HttpServletRequest request){

        Person person = personService.findByEmail(passwordForgot.getEmail());
        PasswordResetToken token = new PasswordResetToken();
        token.setPerson(person);
        token.setToken(UUID.randomUUID().toString());
        token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
        //System.out.println("Token: " + token.getToken() + " dla: " + person.getUsername());
        //System.out.println("http://localhost:8080/reset-password?token=" + token.getToken());

        Mail mail = new Mail();
        mail.setRecipient(person.getEmail());
        mail.setSubject("Reset password");
        mail.setContent("http://localhost:8080/reset-password?token=" + token.getToken());
        mailService.send(mail);

        passwordResetTokenRepository.save(token);
        return "redirect:/";
    }
}
