package net.brylka.BugTrackerJava;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/hello")
    @Secured("ROLE_TEST")
    public String hello() {
        return "test/test";
    }
}
