package com.CHAT01.controller.intent.dto;

import com.CHAT01.command.dto.EventDTO;
import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class DetectIntentRequest {
    private static final int MAX_TEXT_ADMISSIBLE_LENGTH = 256;

    @NotNull
    @Size(min = 1, max = 36, message = "Session ID must be present with length not exceeding 36 characters.")
    private String session;

    @NotNull
    private ChannelEnum channel;

    /* mutuamente esclusivi */
    private String text;
    @Valid
    private EventDTO event;

    @Valid
    private ParametersDTO parameters;

    @AssertTrue
    private boolean isValidTextOrEventDetectIntentRequest() {
        return !StringUtils.isBlank(text) ||
                (event != null && !StringUtils.isBlank(event.getName()));
    }

    @AssertTrue
    private boolean textNotTooLongDetectIntentRequest() {
        return StringUtils.isBlank(text) || text.length() <= MAX_TEXT_ADMISSIBLE_LENGTH;
    }
}
