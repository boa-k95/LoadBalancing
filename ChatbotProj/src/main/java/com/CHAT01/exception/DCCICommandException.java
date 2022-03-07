package com.CHAT01.exceptions;

import com.intesasanpaolo.bear.exceptions.BearDomainRuntimeException;
import org.springframework.http.HttpStatus;

public class DCCICommandException extends BearDomainRuntimeException {

    public DCCICommandException(String message, String code, HttpStatus responseStatus) {
        super(message, code, responseStatus);
    }

    public DCCICommandException(String message, String code) {
        super(message, code, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private static final long serialVersionUID = 9039374136548217879L;

}
