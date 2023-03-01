package net.brylka.BugTrackerJava.passwordrecovery;

import jakarta.persistence.*;
import lombok.Data;
import net.brylka.BugTrackerJava.people.Person;


import java.time.LocalDateTime;

@Data
@Entity
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String token;
    @OneToOne(targetEntity = Person.class, fetch = FetchType.EAGER)
    private Person person;
    @Column(nullable = false)
    private LocalDateTime expirationDate;

}