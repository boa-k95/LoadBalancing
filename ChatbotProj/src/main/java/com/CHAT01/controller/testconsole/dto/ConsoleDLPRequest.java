package com.CHAT01.consoleDLP.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ConsoleDLPRequest {
    //@NotNull
    @NotEmpty
    private String text;
}
