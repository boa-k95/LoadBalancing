package com.CHAT01.controller.intent.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class ChatDTO {
    @NotNull
    private String type;
    @NotNull
    @NotEmpty
    private String room;

    private String operator;
}
