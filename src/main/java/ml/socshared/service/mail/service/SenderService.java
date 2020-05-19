package ml.socshared.service.mail.service;

import ml.socshared.service.mail.domain.SendMessageRequest;
import ml.socshared.service.mail.domain.SuccessResponse;

public interface SenderService {

    SuccessResponse send(SendMessageRequest request);

}
