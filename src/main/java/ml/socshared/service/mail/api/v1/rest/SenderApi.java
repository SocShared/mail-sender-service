package ml.socshared.service.mail.api.v1.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import ml.socshared.service.mail.domain.request.SendMessageGeneratingCodeRequest;
import ml.socshared.service.mail.domain.request.SendMessageRequest;
import ml.socshared.service.mail.domain.response.SuccessResponse;

import javax.validation.Valid;

@Api(value = "Sender Controller")
public interface SenderApi {

    @ApiOperation(value = "Отправка сообщения", notes = "Отправка сообщения")
    SuccessResponse send(@Valid SendMessageRequest request);
    @ApiOperation(value = "Отправка сообщения для подтверждения электронной почты")
    SuccessResponse sendMailConfirm(@Valid SendMessageGeneratingCodeRequest request);
    @ApiOperation(value = "Отправка сообщения для сброса пароля")
    SuccessResponse sendPasswordReset(@Valid SendMessageGeneratingCodeRequest request);
}
