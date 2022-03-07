package com.CHAT01.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.List;

@Data
public class CleverPractice {
    private String bankBranchNumber;
    private String bankBranchAddress;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private String currentAction;
    private Boolean hasEmail;
    private Integer totalRegisteredPromises;
    private PracticeHesitationPaymentPromiseTypeEnum lastRegisteredPromiseType;
    private Integer totalRegisteredAppointments;
    private LocalDate registeredAppointmentDate;
    private OffsetTime registeredAppointmentTime;
    private LocalDate registeredPaymentPromiseDate;
    private List<CleverProduct> notInstallmentsProducts;
    private List<CleverProduct> installmentsProducts;
    private Double totalAmountOwed;
    private Double maxAmountOwed;
    private String certifiedTelephoneNumber;
    private Boolean hasAtLeastOne1100Product;
}
