package com.CHAT01.controller.intent.dto;

import com.CHAT01.command.dto.OutputMessage;
import lombok.Data;

import java.util.List;

@Data
public class DetectResponse {

    private String session;
    //private String text;
    //private OutputParameters parameters;
    private List<OutputMessage> messages;

    private int messageNumber;
    private int ack;

}
