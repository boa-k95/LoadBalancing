package com.CHAT01.controller.intent.dto;


import com.CHAT01.command.dto.EventDTO;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data // Serve per test
public class DetectRequest {

    @NotNull
    @Size(min = 1, max = 36, message = "Session id must be present with length not exceeding 36 characters.")
    private String session;

    /* Mutualmente esclusivi */
    private String text;
    private EventDTO event;

    private ParametersDTO parameters;

   @NotNull
    @Valid
    private CustomerDTO customer;

   /* @NotNull
    @Valid
    private ChatDTO chat;
*/
    @NotNull
    private ChannelEnum channel;

    @AssertTrue
    private boolean isValidTextOrEventDetectIntentRequest() {
        return !StringUtils.isBlank(text) ||
                (event != null && !StringUtils.isBlank(event.getName()));
    }
}
