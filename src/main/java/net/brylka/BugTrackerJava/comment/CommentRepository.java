package net.brylka.BugTrackerJava.comment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByIssueId(Long issueId);

    void deleteByIssueId(Long id);
}