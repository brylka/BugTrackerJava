package net.brylka.BugTrackerJava.authority;

import net.brylka.BugTrackerJava.enums.AuthorityEnum;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Authority findByName(AuthorityEnum name);
}
