package com.CHAT01.model;

public enum InfoTypeEnum {
    ADVERTISING_ID("", false),
    AGE("", false),
    CREDIT_CARD_NUMBER("", false),
    CREDIT_CARD_TRACK_NUMBER("", false),
    DATE_OF_BIRTH("", false),
    EMAIL_ADDRESS("", false),
    ETHNIC_GROUP("", false),
    FEMALE_NAME("", false),
    // GENERIC_ID("", false),
    IBAN_CODE("", false),
    ICD9_CODE("", false),
    ICD10_CODE("", false),
    ITALY_FISCAL_CODE("", false),
    MALE_NAME("", false),
    MEDICAL_TERM("", false),
    PASSPORT("", false),
    PERSON_NAME("", false),
    PHONE_NUMBER("", false),
    IP_ADDRESS("", false),
    STREET_ADDRESS("", false),
    SWIFT_CODE("", false),
    URL("", false),
    /* Custom InfoTypes */
    ITALY_TARGA("[GENERIC_ID]", true),
    ITALY_VAT("[GENERIC_ID]", true),
    ITALY_CARTA_IDENTITA("[GENERIC_ID]", true);

    private final String replacement;
    private final boolean custom;

    InfoTypeEnum(String rep, boolean isCustom) {
        replacement = rep;
        custom = isCustom;
    }

    public String getReplacement() {
        return replacement;
    }

    public boolean isCustom() {
        return custom;
    }
}
