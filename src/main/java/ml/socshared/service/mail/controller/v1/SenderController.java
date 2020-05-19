package ml.socshared.service.mail.controller.v1;

import lombok.RequiredArgsConstructor;
import ml.socshared.service.mail.domain.SendMessageRequest;
import ml.socshared.service.mail.domain.SuccessResponse;
import ml.socshared.service.mail.service.SenderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import ml.socshared.service.mail.api.v1.rest.SenderApi;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/api/v1")
@RequiredArgsConstructor
@Validated
public class SenderController implements SenderApi {

    private final SenderService service;

    @Override
    @PostMapping(value = "/message", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResponse send(@Valid @RequestBody SendMessageRequest request) {
        return service.send(request);
    }
}
