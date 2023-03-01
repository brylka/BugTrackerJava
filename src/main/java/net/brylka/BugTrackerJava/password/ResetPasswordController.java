package net.brylka.BugTrackerJava.password;

import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.people.PersonService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/reset-password")
public class ResetPasswordController {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final PersonService personService;

    public ResetPasswordController(PasswordResetTokenRepository passwordResetTokenRepository, PersonService personService) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.personService = personService;
    }

    @GetMapping
    ModelAndView viewPage(@RequestParam(name = "token", required = false) String token,
                          Model model){
        ModelAndView modelAndView = new ModelAndView("password/reset-password");
        modelAndView.addObject("passwordReset", new PasswordReset());
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if(passwordResetToken == null || passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now())){
            return new ModelAndView("redirect:/");
        }
        model.addAttribute("token", passwordResetToken.getToken());
        return modelAndView;
    }

    @PostMapping
    public String processPasswordReset(@RequestParam(name = "token", required = false) String token,
                                       @RequestParam(name = "password", required = false) String password,
                                       @RequestParam(name = "confirmPassword", required = false) String confirmPassword){
        //System.out.println("Token: " + token + " Password: " + password + " Confirm Password: " + confirmPassword);
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        Person person = passwordResetToken.getPerson();
        person.setPassword(password);
        personService.savePersonAccount(person);
        passwordResetTokenRepository.delete(passwordResetToken);

        return "redirect:/";
    }
}
