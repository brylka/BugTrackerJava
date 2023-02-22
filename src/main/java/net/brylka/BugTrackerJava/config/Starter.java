package net.brylka.BugTrackerJava.config;

import net.brylka.BugTrackerJava.authority.Authority;
import net.brylka.BugTrackerJava.authority.AuthorityRepository;
import net.brylka.BugTrackerJava.enums.AuthorityEnum;
import net.brylka.BugTrackerJava.people.PersonService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class Starter implements InitializingBean {

    private final AuthorityRepository authorityRepository;
    private final PersonService personService;

    public Starter(AuthorityRepository authorityRepository, PersonService personService) {
        this.authorityRepository = authorityRepository;
        this.personService = personService;
    }

    @Override
    public void afterPropertiesSet() {
        System.out.println("Przygotowanie apki...");

        prepareAuthorities();
        personService.prepareAdminUser();
    }

    private void prepareAuthorities() {
        System.out.println("Przygotowanie uprawnień...");
        for (AuthorityEnum name : AuthorityEnum.values()) {
            Authority existingAuthority = authorityRepository.findByName(name);
            if (existingAuthority == null) {
                System.out.println("Tworzymy uprawnienie: " + name);
                Authority authority = new Authority(name);
                authorityRepository.save(authority);
            }
        }
    }
}
