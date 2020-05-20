package ml.socshared.service.mail.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ml.socshared.service.mail.domain.SendMessageRequest;
import ml.socshared.service.mail.service.SenderService;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RabbitMQService {

    private final SenderService senderService;

    public void receiveMessage(SendMessageRequest request) {
        senderService.send(request);
    }


}
