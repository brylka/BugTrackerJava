package net.brylka.BugTrackerJava.people;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>, JpaSpecificationExecutor<Person> {
    Person findByUsernameAndEnabled(String username, Boolean enabled);

    Person findByUsername(String name);
}
