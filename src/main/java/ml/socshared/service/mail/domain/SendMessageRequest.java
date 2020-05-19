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

    @NonNull
    private String text;
    @NonNull
    private String subject;
    @Email
    private String fromEmail;
    @NonNull
    private ArrayList<String> toEmails;

}
