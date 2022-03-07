package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;
import com.CHAT01.model.InfoTypeEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Data
public class Transformation {
    private static final Logger logger = LoggerFactory.getLogger(Transformation.class);

    private List<InfoType> infoTypes;
    private PrimitiveTransformation primitiveTransformation;

    public Transformation(InfoTypeEnum infoTypeValue) {
        InfoType infoType = new InfoType();
        infoType.setName(infoTypeValue);
        infoTypes = Collections.singletonList(infoType);

        primitiveTransformation = new PrimitiveTransformation();
        if (StringUtils.isNotBlank(infoTypeValue.getReplacement())) {
            ReplaceConfig replaceConfig = new ReplaceConfig();
            NewValue newValue = new NewValue();
            newValue.setStringValue(infoTypeValue.getReplacement());
            replaceConfig.setNewValue(newValue);
            primitiveTransformation.setReplaceConfig(replaceConfig);
        } else {
            primitiveTransformation.setReplaceWithInfoTypeConfig(new Object());
        }
    }
}
