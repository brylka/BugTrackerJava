package net.brylka.BugTrackerJava.people;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.brylka.BugTrackerJava.authority.Authority;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table( name = "person")
public class Person {

    @Id
    @GeneratedValue
    Long id;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false, unique = true)
    String username;

    @Column(nullable = false)
    //@NotBlank
    String password;

    @NotBlank
    @Size(max = 255)
    @Column(nullable = false)
    String name;

    @NotBlank
    @Column(nullable = false)
    @Email
    String email;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    Boolean enabled = true;

    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(name = "person_authorities",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id"))
    Set<Authority> authorities;

    public Person(String username, String password, String name, String email) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
    }
}