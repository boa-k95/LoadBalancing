package com.CHAT01.model;

import lombok.Data;

import java.util.List;

@Data
public class CleverCustomer {
    private Boolean multiPractice;
    private String sndg;
    //    private String bankBranchNumber;
//    private String bankBranchAddress;
//    private LocalDate startDate;
//    private LocalDate expiryDate;
//    private String currentAction;
//    private Boolean hasEmail;
//    private Integer totalRegisteredPromises;
//    private Integer totalRegisteredAppointments;
//    private LocalDate registeredAppointmentDate;
//    private OffsetTime registeredAppointmentTime;
//    private LocalDate registeredPaymentPromiseDate;
//    private List<CleverProduct> outstandings;
//    private List<CleverProduct> installmentsProducts;
//    private Double totalAmountOwed;
//    private Double maxAmountOwed;
//    private String certifiedTelephoneNumber;
//    private Boolean hasAtLeastOne1100Product;
    private List<CleverPractice> openPractices;
    private List<CleverPractice> closedPractices;
}
