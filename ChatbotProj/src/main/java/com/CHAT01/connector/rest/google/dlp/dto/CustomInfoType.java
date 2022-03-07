package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;
import com.CHAT01.model.InfoTypeEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class CustomInfoType {
    public static final CustomInfoType ITALY_TARGA = new CustomInfoType(InfoTypeEnum.ITALY_TARGA,
            "[a-zA-Z]{2}\\s*\\d{3}\\s*[a-zA-Z]{2}", Collections.singletonList(new HotwordRule("[Tt]arga", 50, 50, 1)));
    public static final CustomInfoType ITALY_CARTA_IDENTITA = new CustomInfoType(InfoTypeEnum.ITALY_CARTA_IDENTITA,
            "C[a-zA-Z]\\d{5}[a-zA-Z]{2}", Collections.singletonList(new HotwordRule("[Cc][Ii]|[Cc]arta\\s+(di\\s+|d')?[Ii]dentit[aà]", 50, 50, 1)));
    public static final CustomInfoType ITALY_VAT = new CustomInfoType(InfoTypeEnum.ITALY_VAT,
            "(IT)?\\d{11}", Collections.singletonList(new HotwordRule("[Pp]\\.?\\s*[Ii]\\.?|[Pp]\\.?\\s*[Ii](VA|va)|[Pp]artita\\s+[Ii][Vv][Aa]", 50, 50, 1)));

    @Data
    public class InfoTypeRegex {
        private String pattern;
        private List<Integer> groupIndexes;
    }

    private InfoType infoType;
    private List<DetectionRule> detectionRules;
    private InfoTypeRegex regex;
    private DLPLikelihood likelihood;

    private CustomInfoType(InfoTypeEnum name, String pattern, List<HotwordRule> hotwordRules) {
        infoType = new InfoType();
        infoType.setName(name);

        regex = new InfoTypeRegex();
        regex.setPattern(pattern);

        detectionRules = new ArrayList<>();
        for (HotwordRule hotwordRule : hotwordRules) {
            DetectionRule detectionRule = new DetectionRule();
            detectionRule.setHotwordRule(hotwordRule);
            detectionRules.add(detectionRule);
        }

        likelihood = DLPLikelihood.LIKELY;
    }
}
