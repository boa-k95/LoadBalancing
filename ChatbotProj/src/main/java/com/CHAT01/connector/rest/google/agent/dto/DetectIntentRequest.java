package com.CHAT01.connector.rest.google.agent.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DetectIntentRequest {

    @Data
    public class DetectIntentQueryText {
        private String text;
        private String languageCode;
    }

    @Data
    public class DetectIntentQueryEvent {
        private String name;
        private String languageCode;
        @JsonInclude(Include.ALWAYS)
        private Map<String, Object> parameters;
    }

    @Data
    public class DetectIntentQueryContext {
        private Integer lifespanCount;
        private String name;
        private Map<String, Object> parameters;
    }

    @Data
    public class DetectIntentQueryInput {
        /* Mutualmente esclusivi: settarne solo uno a seconda che la comunicazione sia tecnica o meno */
        private DetectIntentQueryText text;
        private DetectIntentQueryEvent event;
    }

    @Data
    public class DetectIntentQueryParams {
        private List<DetectIntentQueryContext> contexts;
        private String timeZone;
        private Map<String, Object> parameters;
        private boolean resetContexts;
        private String source;
        /* Da aggiungere se necessaria */
        // private QuerySentimentAnalysisRequestConfigParam sentimentAnalysisRequestConfig
    }

    private DetectIntentQueryInput queryInput;
    private DetectIntentQueryParams queryParams;
}
