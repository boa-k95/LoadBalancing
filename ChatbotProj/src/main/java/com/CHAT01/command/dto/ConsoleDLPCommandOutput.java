package com.CHAT01.command.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ConsoleDLPCommandOutput  {
    private String MaskedText;
    private String OriginalText;

}
