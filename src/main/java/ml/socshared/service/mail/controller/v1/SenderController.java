package ml.socshared.service.mail.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.service.mail.domain.request.SendMessageGeneratingCodeRequest;
import ml.socshared.service.mail.domain.request.SendMessageRequest;
import ml.socshared.service.mail.domain.response.SuccessResponse;
import ml.socshared.service.mail.service.SenderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ml.socshared.service.mail.api.v1.rest.SenderApi;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
@Validated
public class SenderController implements SenderApi {

    private final SenderService service;

    @Override
    @PostMapping(value = "/private/message")
    public SuccessResponse send(@Valid @RequestBody SendMessageRequest request) {
        return service.send(request);
    }

    @Override
    @PostMapping(value = "/private/message/confirm/mail")
    public SuccessResponse sendMailConfirm(@Valid @RequestBody SendMessageGeneratingCodeRequest request) {
        return service.sendMailConfirm(request);
    }

    @Override
    @PostMapping(value = "/private/message/password/reset")
    public SuccessResponse sendPasswordReset(@Valid @RequestBody SendMessageGeneratingCodeRequest request) {
        return service.sendPasswordReset(request);
    }
}
