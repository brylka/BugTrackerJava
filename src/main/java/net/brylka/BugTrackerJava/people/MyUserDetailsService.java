package net.brylka.BugTrackerJava.people;

import jakarta.transaction.Transactional;
import net.brylka.BugTrackerJava.authority.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {
    private final PersonRepository personRepository;

    public MyUserDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = personRepository.findByUsernameAndEnabled(username, true);
        return buildUserDetails(person);
    }

    private UserDetails buildUserDetails(Person person) {
        List<GrantedAuthority> authorities = getUserAuthorities(person);
        return new User(person.username, person.password, authorities);
    }

    private List<GrantedAuthority> getUserAuthorities(Person person) {
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Authority authority : person.authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName().toString()));
        }
        return new ArrayList<>(grantedAuthorities);
    }
}
