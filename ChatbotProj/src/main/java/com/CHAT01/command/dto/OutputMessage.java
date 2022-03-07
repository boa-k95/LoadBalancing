package com.CHAT01.command.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import model.OutputParameters;

@Data
@AllArgsConstructor
public class OutputMessage {
    private String response;

    /* Informazioni da restituire all'app al termine di una conversazione */
    private OutputParameters outputParameters;
}
