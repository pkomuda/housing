package pl.dmcs.pkomuda.housing.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Map;

@EnableAsync
@Configuration
public class EmailConfiguration {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private Integer port;

    @Value("${mail.username}")
    private String username;

    @Value("${mail.password}")
    private String password;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);
        mailSender.getJavaMailProperties().putAll(Map.of(
                "mail.transport.protocol", "smtp",
                "mail.smtp.auth", true,
                "mail.smtp.starttls.enable", true,
                "mail.smtp.ssl.protocols", "TLSv1.2"
        ));
        return mailSender;
    }
}
