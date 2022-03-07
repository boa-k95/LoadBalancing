package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;
import com.CHAT01.model.InfoTypeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Data
public class InspectConfig {
    private static final Logger logger = LoggerFactory.getLogger(InspectConfig.class);

    private static final String CONTENT_TEXT = "CONTENT_TEXT";

    private List<InfoType> infoTypes;
    private DLPLikelihood minLikelihood;
    private List<CustomInfoType> customInfoTypes;
    private List<String> contentOptions;
    private List<IntrospectionRules> ruleSet;

    public InspectConfig(List<InfoTypeEnum> infoTypeValues) {
        this.infoTypes = new LinkedList<>();
        for (InfoTypeEnum infoTypeValue : infoTypeValues) {
            InfoType infoType = new InfoType();
            infoType.setName(infoTypeValue);
            this.infoTypes.add(infoType);
        }

        minLikelihood = DLPLikelihood.VERY_UNLIKELY;

//        customInfoTypes = new LinkedList<>();
//        customInfoTypes.add(CustomInfoType.ITALY_TARGA);
//        customInfoTypes.add(CustomInfoType.ITALY_CARTA_IDENTITA);
//        customInfoTypes.add(CustomInfoType.ITALY_VAT);

        contentOptions = new LinkedList<>();
        contentOptions.add(CONTENT_TEXT);

        ruleSet = Collections.emptyList();
    }
}
