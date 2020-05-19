package ml.socshared.service.mail.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.mail.domain.SendMessageRequest;
import ml.socshared.service.mail.domain.SuccessResponse;
import ml.socshared.service.mail.service.SenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

@Service
@Slf4j
public class SenderServiceImpl implements SenderService {

    @Value("${mail.username}")
    private String username;
    @Value("${mail.password}")
    private String password;
    @Value("${mail.smtp.host}")
    private String mailSmtpHost;
    @Value("${mail.smtp.socketFactoryPost}")
    private String mailSmtpSocketFactoryPort;
    @Value("${mail.smtp.socketFactoryClass}")
    private String mailSmtpSocketFactoryClass;
    @Value("${mail.smtp.auth}")
    private String mailSmtpAuth;
    @Value("${mail.smtp.port}")
    private String mailSmtpPort;
    @Value("${mail.from.email}")
    private String fromEmail;


    @Override
    public SuccessResponse send(SendMessageRequest request) {
        Properties props = new Properties();
        props.put("mail.smtp.host", mailSmtpHost);
        props.put("mail.smtp.socketFactory.port", mailSmtpSocketFactoryPort);
        props.put("mail.smtp.socketFactory.class", mailSmtpSocketFactoryClass);
        props.put("mail.smtp.auth", mailSmtpAuth);
        props.put("mail.smtp.port", mailSmtpPort);
        Session session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        ExecutorService service = Executors.newSingleThreadExecutor();

        service.submit(() -> {
            List<String> emails = request.getToEmails();
            for (String email : emails) {
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(fromEmail));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                    message.setSubject(request.getSubject());
                    message.setContent(request.getText(), "text/html; charset=utf-8");

                    Transport.send(message);
                } catch (MessagingException exc) {
                    log.error(exc.getMessage());
                }
            }
        });

        return SuccessResponse.builder().success(true).build();
    }
}
