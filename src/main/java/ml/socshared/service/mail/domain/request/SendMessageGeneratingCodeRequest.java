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
public class SendMessageGeneratingCodeRequest {

    @NotNull
    private String subject;
    @NotNull
    private String username;
    @NotNull
    private String link;
    @Email
    @NotNull
    private String toEmail;

}
