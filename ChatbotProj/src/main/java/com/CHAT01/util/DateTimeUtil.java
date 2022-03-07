package com.CHAT01.util;

import com.CHAT01.exceptions.MalformedTimePeriodException;
import lombok.Data;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class DateTimeUtil {
    public static final DateTimeFormatter DIALOGFLOW_DATE_FORMATTER = DateTimeFormatter.ISO_DATE;
    public static final DateTimeFormatter DIALOGFLOW_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_TIME;
    public static final DateTimeFormatter DIALOGFLOW_DATE_TIME_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
    public static final String DIALOGFLOW_TIME_EXTREMES_SEPARATOR = "-";
    public static final String DIALOGFLOW_TIME_EXTREMES_TEMPLATE = "%s" + DIALOGFLOW_TIME_EXTREMES_SEPARATOR + "%s";

    public static final DateTimeFormatter PULSE_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public static final DateTimeFormatter TLC_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter TLC_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static final DateTimeFormatter FRONT_END_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public static final DateTimeFormatter FRONT_END_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final String FRONT_END_TIME_EXTREMES_SEPARATOR = "-";
    public static final String FRONT_END_TIME_EXTREMES_TEMPLATE = "%s" + FRONT_END_TIME_EXTREMES_SEPARATOR + "%s";

    public static final ZoneId ROME_ZONE_ID = ZoneId.of("Europe/Rome");
    public static final ZoneOffset OFFSET_ROME_ZONE_ID = ROME_ZONE_ID.getRules().getOffset(LocalDateTime.now());

    public static final String START_TIME_KEY = "startTime";
    public static final String END_TIME_KEY = "endTime";

    private DateTimeUtil() {
    }

    @Data
    public static class LocalTimeExtremes {
        private static DateTimeFormatter timeFormatter = DIALOGFLOW_TIME_FORMATTER;

        private OffsetTime startTime;
        private OffsetTime endTime;

        @Override
        public String toString() {
            return String.format(DIALOGFLOW_TIME_EXTREMES_TEMPLATE, startTime.format(timeFormatter), endTime.format(timeFormatter));
        }

        public static LocalTimeExtremes fromString(String string) {
            String[] extremes = string.split(DIALOGFLOW_TIME_EXTREMES_SEPARATOR);
            LocalTimeExtremes toReturn = new LocalTimeExtremes();
            toReturn.setStartTime(OffsetTime.of(LocalTime.parse(extremes[0], timeFormatter), OFFSET_ROME_ZONE_ID));
            toReturn.setEndTime(OffsetTime.of(LocalTime.parse(extremes[1], timeFormatter), OFFSET_ROME_ZONE_ID));
            return toReturn;
        }
    }


    public static LocalTimeExtremes generateLocalTimeExtremes(Map<String, String> extremes) throws MalformedTimePeriodException {
        LocalTimeExtremes toReturn;
        try {
            toReturn = new LocalTimeExtremes();
            toReturn.setStartTime(OffsetTime.parse(extremes.get(START_TIME_KEY), DIALOGFLOW_DATE_TIME_FORMATTER));
            toReturn.setEndTime(OffsetTime.parse(extremes.get(END_TIME_KEY), DIALOGFLOW_DATE_TIME_FORMATTER));
        } catch (Exception dateTimeParseException) {
            throw new MalformedTimePeriodException("Malformed time-period '" + extremes + "': " + dateTimeParseException.getMessage());
        }
        return toReturn;
    }
}
