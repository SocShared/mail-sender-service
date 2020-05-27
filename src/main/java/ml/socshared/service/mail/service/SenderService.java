package ml.socshared.service.mail.service;

import ml.socshared.service.mail.domain.request.SendMessageMailConfirmRequest;
import ml.socshared.service.mail.domain.request.SendMessageRequest;
import ml.socshared.service.mail.domain.response.SuccessResponse;

public interface SenderService {

    SuccessResponse send(SendMessageRequest request);
    SuccessResponse send(SendMessageMailConfirmRequest request);

}
