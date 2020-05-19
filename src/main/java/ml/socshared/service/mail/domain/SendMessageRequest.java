package ml.socshared.service.mail.domain;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class SendMessageRequest {

    private String text;
    private String subject;
    private ArrayList<String> toEmails;

}
