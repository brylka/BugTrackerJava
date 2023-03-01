package net.brylka.BugTrackerJava.mail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {

    String recipient;

    String subject;

    String content;

    public Mail(String from, String subject, String content) {
        this.recipient = from;
        this.subject = subject;
        this.content = content;
    }
}
