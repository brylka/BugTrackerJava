package net.brylka.BugTrackerJava.project;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 200)
    private String name;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotBlank
    @Size(max = 20)
    private String code;

    @Column(nullable = false)
    @ColumnDefault(value = "true")
    private Boolean enabled  = true;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable=false)
    private Date dateCreated;

    public Project(String name, String description, String code) {
        this.name = name;
        this.description = description;
        this.code = code;
    }
}
