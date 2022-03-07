package com.CHAT01.service.google.dlp.dto;

import com.CHAT01.connector.rest.google.dlp.dto.InfoTypeTransformations;
import com.CHAT01.model.InfoTypeEnum;

import lombok.Data;

import java.util.List;

@Data
public class DeidentifyConfig {
    private InfoTypeTransformations infoTypeTransformations;

    public DeidentifyConfig(List<InfoTypeEnum> types) {
        infoTypeTransformations = new InfoTypeTransformations(types);
    }
}
