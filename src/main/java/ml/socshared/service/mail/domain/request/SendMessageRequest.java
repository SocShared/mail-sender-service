package ml.socshared.service.mail.domain.request;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class SendMessageRequest {

    @NotNull
    private String text;
    @NotNull
    private String subject;
    @NotNull
    @Email
    private String fromEmail;
    @NotNull
    private ArrayList<String> toEmails;

}
