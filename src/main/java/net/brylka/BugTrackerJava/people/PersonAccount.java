package net.brylka.BugTrackerJava.people;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.brylka.BugTrackerJava.authority.Authority;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PersonAccount {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String username;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Email
    private String email;

    public PersonAccount(Person person) {
        this.id = person.id;
        this.username = person.username;
        this.name = person.name;
        this.email = person.email;
    }
}
