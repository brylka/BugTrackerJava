package net.brylka.BugTrackerJava.people;

import net.brylka.BugTrackerJava.authority.Authority;
import net.brylka.BugTrackerJava.authority.AuthorityRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    private final AuthorityRepository authorityRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${my.admin.username}")
    private String myAdminUsername;
    @Value("${my.admin.password}")
    private String myAdminPassword;
    @Value("${my.admin.name}")
    private String myAdminName;
    @Value("${my.admin.email}")
    private String myAdminEmail;

    public PersonService(AuthorityRepository authorityRepository,
                         PersonRepository personRepository,
                         BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authorityRepository = authorityRepository;
        this.personRepository = personRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void prepareAdminUser() {

        if (personRepository.findByUsernameAndEnabled(myAdminUsername, true) == null) {

            System.out.println("Tworzymy administratora: login: " + myAdminUsername + " passwd: " +
                    myAdminPassword + " email: " + myAdminEmail + " nazwa: " + myAdminName);
            Person person = new Person(myAdminUsername, myAdminPassword, myAdminName, myAdminEmail);

            System.out.println("Ustawiamy uprawnienia administratora");
            List<Authority> authorities = (List<Authority>) authorityRepository.findAll();
            person.setAuthorities(new HashSet<>(authorities));

            savePersonAccount(person);
        }
    }

    public void savePersonAccount(Person person) {
        String hashedPassword = bCryptPasswordEncoder.encode(person.password);
        person.setPassword(hashedPassword);
        personRepository.save(person);
    }

    protected void savePersonAccountWithoutHash(Person person) {
        personRepository.save(person);
    }

    protected List<Authority> findAuthorities() {
        return (List<Authority>) authorityRepository.findAll();
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public void markPersonAsDeleted(Long id) {
        Person person = findById(id);
        person.setDeleted(true);
        personRepository.save(person);
    }

    public Person editPerson(Long id) {
        return personRepository.findById(id).orElseThrow();
    }

    public void savePersonAccount(PersonAccount personAccount) {
        Person person = personRepository.findById(personAccount.getId()).orElseThrow();
        person.setUsername(personAccount.getUsername());
        person.setName(personAccount.getName());
        person.setEmail(personAccount.getEmail());
        personRepository.save(person);
    }

    public void savePersonPassword(PersonPassword personPassword) {
        Person person = personRepository.findById(personPassword.getId()).orElseThrow();
        String hashedPassword = bCryptPasswordEncoder.encode(personPassword.getPassword());
        person.setPassword(hashedPassword);
        personRepository.save(person);
    }

    public Person findById(Long id) {
        Optional<Person> result = personRepository.findById(id);
        return result.orElse(null);
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
