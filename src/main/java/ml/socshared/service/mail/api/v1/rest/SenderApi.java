package ml.socshared.service.mail.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.service.mail.domain.SendMessageRequest;
import ml.socshared.service.mail.domain.SuccessResponse;

import java.util.HashMap;

@Api(value = "Sender Controller")
public interface SenderApi {

    @ApiOperation(value = "Отправка сообщения", notes = "Отправка сообщения")
    SuccessResponse send(SendMessageRequest request);
}
