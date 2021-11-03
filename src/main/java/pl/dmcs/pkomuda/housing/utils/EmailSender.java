package pl.dmcs.pkomuda.housing.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pl.dmcs.pkomuda.housing.exceptions.ApplicationBaseException;
import pl.dmcs.pkomuda.housing.model.EmailAttachment;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
@RequiredArgsConstructor
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Async
    public void sendMessage(String to, String subject, String text, EmailAttachment attachment) throws ApplicationBaseException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            if (attachment != null) {
                helper.addAttachment(attachment.getFileName(), attachment.getDataSource());
            }
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new ApplicationBaseException(e);
        }
    }
}
