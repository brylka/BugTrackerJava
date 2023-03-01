package net.brylka.BugTrackerJava;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "redirect:/hello";
    }

    @RequestMapping("/hello")
    //@Secured("ROLE_ADMIN")
    public String hello() {
        return "test/test";
    }
}