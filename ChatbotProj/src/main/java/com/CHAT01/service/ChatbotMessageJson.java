package com.CHAT01.service;

import lombok.Data;
import com.CHAT01.model.OutputParameters;

@Data
public class ChatbotMessageJson {
    private String Text;
    private OutputParameters appOutputParameters;

}
