package net.brylka.BugTrackerJava.mail;

import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    final private JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void send(Mail mail) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

            mimeMessageHelper.setTo(mail.recipient);
            mimeMessageHelper.setSubject(mail.subject);
            mimeMessageHelper.setText(mail.content);

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            System.out.println("Wysyłanie mejla nie powiodło się " + e);
        }
    }
}
