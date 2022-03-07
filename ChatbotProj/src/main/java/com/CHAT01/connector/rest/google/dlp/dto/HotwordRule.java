package com.CHAT01.connector.rest.google.dlp.dto;

import lombok.Data;

@Data
public class HotwordRule {
    private HotwordRegex hotwordRegex;
    private HotwordProximity proximity;
    private HotwordLikelihoodAdjustment likelihoodAdjustment;

    public HotwordRule(String regexPattern, int windowBefore, int windowAfter, int relativeLikelihood) {
        this.hotwordRegex = new HotwordRegex();
        hotwordRegex.setPattern(regexPattern);

        this.proximity = new HotwordProximity();
        proximity.setWindowAfter(windowAfter);
        proximity.setWindowBefore(windowBefore);

        this.likelihoodAdjustment = new HotwordLikelihoodAdjustment();
        likelihoodAdjustment.setRelativeLikelihood(relativeLikelihood);
    }
}
