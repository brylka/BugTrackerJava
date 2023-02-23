package net.brylka.BugTrackerJava.issue;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.brylka.BugTrackerJava.enums.Priority;
import net.brylka.BugTrackerJava.enums.State;
import net.brylka.BugTrackerJava.enums.Type;
import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.project.Project;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Issue {

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

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Person creator;

    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private Person assignee;

    @Enumerated(EnumType.STRING)
    private State state;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public Issue(String name, String description, String code, Project project, Person creator, Person assignee, State state, Type type, Priority priority) {
        this.name = name;
        this.description = description;
        this.code = code;
        this.project = project;
        this.creator = creator;
        this.assignee = assignee;
        this.state = state;
        this.type = type;
        this.priority = priority;
    }
}
