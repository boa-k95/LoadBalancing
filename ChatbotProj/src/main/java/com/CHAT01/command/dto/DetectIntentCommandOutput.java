package com.CHAT01.command.dto;

import lombok.Data;


import java.util.List;

@Data
public class DetectIntentCommandOutput {


    /* PARAMETRI DI RITORNO PER TEST E DEBUG */
    private List<OutputMessage> reponses;

    private String roomId;

    /* PARAMETRI DI RITORNO PER IL CHIAMANTE AL SERVIZIO */
    private String sessionId;
   private int messageNumber;
    private int ack;
}
