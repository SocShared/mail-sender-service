package ml.socshared.service.mail.exception.impl;

import ml.socshared.service.mail.exception.AbstractRestHandleableException;
import ml.socshared.service.mail.exception.SocsharedErrors;
import org.springframework.http.HttpStatus;

public class HttpBadRequestException extends AbstractRestHandleableException {
    public HttpBadRequestException() {
        super(SocsharedErrors.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }

    public HttpBadRequestException(SocsharedErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }

    public HttpBadRequestException(String message) {
        super(message, SocsharedErrors.BAD_REQUEST, HttpStatus.BAD_REQUEST);
    }
}