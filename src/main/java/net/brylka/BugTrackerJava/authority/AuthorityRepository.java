package net.brylka.BugTrackerJava.authority;

import net.brylka.BugTrackerJava.enums.AuthorityEnum;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByName(AuthorityEnum name);

    Set<Authority> findAllById(String authorities);
}
