package net.brylka.BugTrackerJava.comment;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.brylka.BugTrackerJava.issue.Issue;
import net.brylka.BugTrackerJava.people.Person;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Person author;

    @CreationTimestamp
    @Column(name = "date_created", nullable = false, updatable = false)
    private Date dateCreated;

    public Comment(String description, Issue issue, Person author) {
        this.description = description;
        this.issue = issue;
        this.author = author;
    }
}
