package net.brylka.BugTrackerJava.authority;

import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import net.brylka.BugTrackerJava.enums.AuthorityEnum;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "authority")
public class Authority {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private AuthorityEnum name;

    public Authority(AuthorityEnum name) {
        this.name = name;
    }
}
