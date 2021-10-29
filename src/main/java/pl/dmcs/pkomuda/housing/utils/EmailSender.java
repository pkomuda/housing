package pl.dmcs.pkomuda.housing.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMessage(String to, String subject, String text) throws ApplicationBaseException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        try {
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ApplicationBaseException(e);
        }
    }
}
