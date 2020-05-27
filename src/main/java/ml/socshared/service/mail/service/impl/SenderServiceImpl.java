package ml.socshared.service.mail.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.mail.domain.request.SendMessageMailConfirmRequest;
import ml.socshared.service.mail.domain.request.SendMessageRequest;
import ml.socshared.service.mail.domain.response.SuccessResponse;
import ml.socshared.service.mail.service.SenderService;
import ml.socshared.service.mail.service.sentry.SentryService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
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

    private final SentryService sentryService;

    @Override
    public SuccessResponse send(SendMessageRequest request) {
        if (request.getFromEmail() != null) {
            fromEmail = request.getFromEmail();
        }
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

                    Map<String, String> tags = new HashMap<>();
                    tags.put("type", "send_email");
                    Map<String, Object> extras = new HashMap<>();
                    extras.put("from_email", fromEmail);
                    extras.put("to_email", request.getToEmails().toString());
                    sentryService.logMessage("Send message", tags, extras);
                    Transport.send(message);
                } catch (MessagingException exc) {
                    log.error(exc.getMessage());
                }
            }
        });

        return SuccessResponse.builder().success(true).build();
    }

    @Override
    public SuccessResponse send(SendMessageMailConfirmRequest request) {
        SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                .subject(request.getSubject())
                .fromEmail(request.getFromEmail())
                .toEmails(new ArrayList<>() {{add(request.getToEmail());}})
                .text("Здравствуйте, " + request.getUsername() + ".<br><br>Для того, чтобы воспользоваться всеми услугами сервиса SocShared, " +
                        "подтвердите, пожалуйста, Вашу электронную почту, перейдя по следующей ссылке, <a href=\""+request.getLink()+"\">Подтвердить почту</a>. " +
                        "Срок действия данной ссылки 24 часа.<br><br>" + "" +
                        "С уважением, администрация сервиса SocShared.")
                .build();

        return send(sendMessageRequest);
    }
}
