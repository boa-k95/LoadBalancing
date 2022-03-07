package com.CHAT01.exceptions;

import com.intesasanpaolo.bear.exceptions.BearDomainRuntimeException;
import org.springframework.http.HttpStatus;

public class DCCINoDialogflowResponseException extends BearDomainRuntimeException {

    public DCCINoDialogflowResponseException(String message, String code, HttpStatus responseStatus) {
        super(message, code, responseStatus);
    }
}
