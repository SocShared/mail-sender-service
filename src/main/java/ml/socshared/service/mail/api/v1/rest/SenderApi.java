package ml.socshared.service.mail.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.service.mail.domain.request.SendMessageMailConfirmRequest;
import ml.socshared.service.mail.domain.request.SendMessageRequest;
import ml.socshared.service.mail.domain.response.SuccessResponse;
import org.springframework.web.bind.annotation.RequestBody;

@Api(value = "Sender Controller")
public interface SenderApi {

    @ApiOperation(value = "Отправка сообщения", notes = "Отправка сообщения")
    SuccessResponse send(SendMessageRequest request);
    @ApiOperation(value = "Отправка сообщения для подтверждения электронной почты")
    SuccessResponse sendMailConfirm(@RequestBody SendMessageMailConfirmRequest request);

}
