package ml.socshared.service.mail.domain;

import lombok.*;

import javax.validation.constraints.Email;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class SendMessageRequest {

    private String text;
    private String subject;
    private String fromEmail;
    private ArrayList<String> toEmails;

}
