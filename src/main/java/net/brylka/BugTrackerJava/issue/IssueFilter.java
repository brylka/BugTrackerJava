package net.brylka.BugTrackerJava.issue;

import net.brylka.BugTrackerJava.enums.State;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.brylka.BugTrackerJava.people.Person;
import net.brylka.BugTrackerJava.project.Project;
import org.springframework.data.jpa.domain.Specification;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class IssueFilter {

    Project project;
    State state;
    Person assignee;

    public Specification<Issue> buildQuery() {
        Specification<Issue> specification = Specification.where(null);

        specification = Objects.nonNull(project) ? specification
                .and((root, query, builder) -> builder.equal(root.get("project"), project)) : specification;

        specification = Objects.nonNull(state) ? specification
                .and((root, query, builder) -> builder.equal(root.get("state"), state)) : specification;

        specification = Objects.nonNull(assignee) ? specification
                .and((root, query, builder) -> builder.equal(root.get("assignee"), assignee)) : specification;

        return specification;
    }
}