package ml.socshared.service.mail.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.service.mail.domain.request.SendMessageMailConfirmRequest;
import ml.socshared.service.mail.domain.request.SendMessageRequest;
import ml.socshared.service.mail.domain.response.SuccessResponse;
import ml.socshared.service.mail.service.SenderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ml.socshared.service.mail.api.v1.rest.SenderApi;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@Validated
@PreAuthorize("isAuthenticated()")
public class SenderController implements SenderApi {

    private final SenderService service;

    @Override
    @PostMapping(value = "/private/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SERVICE')")
    public SuccessResponse send(@Valid @RequestBody SendMessageRequest request) {
        return service.send(request);
    }

    @Override
    @PostMapping(value = "/private/message/confirm/mail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('SERVICE')")
    public SuccessResponse sendMailConfirm(@Valid @RequestBody SendMessageMailConfirmRequest request) {
        return service.send(request);
    }
}
