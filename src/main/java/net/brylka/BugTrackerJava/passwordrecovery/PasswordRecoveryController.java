package net.brylka.BugTrackerJava.passwordrecovery;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import net.brylka.BugTrackerJava.mail.Mail;
import net.brylka.BugTrackerJava.mail.MailService;
import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.people.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
@RequestMapping("/password-recovery")
public class PasswordRecoveryController {

    private final PersonService personService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    final private MailService mailService;

    public PasswordRecoveryController(PersonService personService, PasswordResetTokenRepository passwordResetTokenRepository, MailService mailService) {
        this.personService = personService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.mailService = mailService;
    }

    @GetMapping("/forgot")
    ModelAndView viewPage() {
        ModelAndView modelAndView = new ModelAndView("passwordrecovery/forgot");
        modelAndView.addObject("passwordForgot", new PasswordForgot());
        return modelAndView;
    }

    @PostMapping("/forgot")
    public String processPasswordForgot(@Valid @ModelAttribute("passwordForgot") PasswordForgot passwordForgot,
                                        BindingResult result,
                                        Model model,
                                        RedirectAttributes attributes,
                                        HttpServletRequest request) {
        Person person = personService.findByEmail(passwordForgot.getEmail());
        if (person != null) {
            PasswordResetToken token = new PasswordResetToken();
            token.setPerson(person);
            token.setToken(UUID.randomUUID().toString());
            token.setExpirationDate(LocalDateTime.now().plusMinutes(30));
            //System.out.println("Token: " + token.getToken() + " dla: " + person.getUsername());
            System.out.println("http://localhost:8080/password-recovery/reset?token=" + token.getToken());

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            try {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        Mail mail = new Mail();
                        mail.setRecipient(person.getEmail());
                        mail.setSubject("Reset password");
                        mail.setContent("http://localhost:8080/password-recovery/reset?token=" + token.getToken());
                        mailService.send(mail);
                    }
                });
            } finally {
                executorService.shutdown();
            }

            passwordResetTokenRepository.save(token);
        }
        attributes.addFlashAttribute("message", "ok");
        return "redirect:/password-recovery/forgot";
    }

    @GetMapping("/reset")
    ModelAndView viewPage(@RequestParam(name = "token", required = false) String token,
                          Model model) {
        ModelAndView modelAndView = new ModelAndView("passwordrecovery/reset");
        modelAndView.addObject("passwordReset", new PasswordReset());
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            return new ModelAndView("redirect:/");
        }
        model.addAttribute("token", passwordResetToken.getToken());
        return modelAndView;
    }

    @PostMapping("/reset")
    public String processPasswordReset(@RequestParam(name = "token", required = false) String token,
                                       @RequestParam(name = "password", required = false) String password,
                                       @RequestParam(name = "confirmPassword", required = false) String confirmPassword) {
        //System.out.println("Token: " + token + " Password: " + password + " Confirm Password: " + confirmPassword);
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        Person person = passwordResetToken.getPerson();
        person.setPassword(password);
        personService.savePersonAccount(person);
        passwordResetTokenRepository.delete(passwordResetToken);

        return "redirect:/";
    }
}
