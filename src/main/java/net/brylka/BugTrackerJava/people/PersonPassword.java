package net.brylka.BugTrackerJava.people;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonPassword {
    private Long id;

    @NotBlank
    private String password;

    @NotBlank
    private String passwordConfirm;

}
