package ml.socshared.service.mail.exception.impl;

import ml.socshared.service.mail.exception.SocsharedErrors;
import ml.socshared.service.mail.exception.AbstractRestHandleableException;
import org.springframework.http.HttpStatus;

public class HttpNotFoundException extends AbstractRestHandleableException {
    public HttpNotFoundException() {
        super(SocsharedErrors.NOT_FOUND, HttpStatus.NOT_FOUND);
    }

    public HttpNotFoundException(SocsharedErrors errorCode, HttpStatus httpStatus) {
        super(errorCode, httpStatus);
    }

    public HttpNotFoundException(String message) {
        super(message, SocsharedErrors.NOT_FOUND, HttpStatus.NOT_FOUND);
    }
}

