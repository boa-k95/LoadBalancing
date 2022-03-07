package com.CHAT01.connector.rest.google.dlp.dto;

import com.CHAT01.model.InfoTypeEnum;

import java.util.LinkedList;
import java.util.List;

public class InfoTypeTransformations {
    private List<Transformation> transformations;

    public InfoTypeTransformations(List<InfoTypeEnum> infoTypeValues) {
        transformations = new LinkedList<>();
        for (InfoTypeEnum infoTypeValue : infoTypeValues)
            transformations.add(new Transformation(infoTypeValue));
    }
}
