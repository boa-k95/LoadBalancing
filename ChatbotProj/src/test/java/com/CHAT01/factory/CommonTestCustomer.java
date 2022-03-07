package com.CHAT01.test.factory;


import com.CHAT01.controller.intent.dto.CustomerDTO;
import com.CHAT01.model.*;

import java.time.LocalDate;
import java.time.OffsetTime;
import java.util.*;

public class CommonTestCustomer {
    public static final String CUSTOMER_ABI = "01025";
    public static final String CUSTOMER_BT = "06718478";
    public static final CustomerTypeEnum CUSTOMER_TYPE = CustomerTypeEnum.PF;
    public static final String CUSTOMER_BUSINESS_BT = "";
    public static final String CUSTOMER_TAX_CODE = "";
    public static final String CUSTOMER_NSG = "";
    public static final List<String> ACCOUNTS = new LinkedList<>();
    
    private static final LocalDate NOW_DATE = LocalDate.now();
    private static final OffsetTime NOW_TIME = OffsetTime.now();

    public static final Customer CUSTOMER = new Customer();
    public static final Set<StateName> ACCESSES = new HashSet<>();

    static {
        CUSTOMER.setAbi(CUSTOMER_ABI);
        CUSTOMER.setBt(CUSTOMER_BT);
        CUSTOMER.setBusinessBt(CUSTOMER_BUSINESS_BT);
        CUSTOMER.setNsg(CUSTOMER_NSG);
        CUSTOMER.setTaxCode(CUSTOMER_TAX_CODE);
        CUSTOMER.setType(CustomerTypeEnum.PF);
        CUSTOMER.setAccounts(ACCOUNTS);
        ACCESSES.add(StateName.HOMEPAGE);
        ACCESSES.add(StateName.GESTOREDEDICATO);
        ACCESSES.add(StateName.CARDS);
        ACCESSES.add(StateName.EXIT);
        ACCESSES.add(StateName.PULSE);
        CUSTOMER.setAccesses(ACCESSES);
    }

    public static final CustomerDTO CUSTOMER_DTO = new CustomerDTO();
    public static final Map<String, Boolean> ACCESS_DTO = new HashMap<>();

    static {
        CUSTOMER_DTO.setAbi(CUSTOMER_ABI);
        CUSTOMER_DTO.setAccounts(ACCOUNTS);
        CUSTOMER_DTO.setBt(CUSTOMER_BT);
        CUSTOMER_DTO.setType(CustomerTypeEnum.PF);
        CUSTOMER_DTO.setBusinessBt(CUSTOMER_BUSINESS_BT);
        CUSTOMER_DTO.setNsg(CUSTOMER_NSG);
        CUSTOMER_DTO.setTaxCode(CUSTOMER_TAX_CODE);
        CUSTOMER_DTO.setType(CUSTOMER_TYPE);
        ACCESS_DTO.put(StateName.HOMEPAGE.getStateName(), true);
        ACCESS_DTO.put(StateName.GESTOREDEDICATO.getStateName(), true);
        ACCESS_DTO.put(StateName.CARDS.getStateName(), true);
        ACCESS_DTO.put(StateName.EXIT.getStateName(), true);
        ACCESS_DTO.put(StateName.PULSE.getStateName(), true);
        CUSTOMER_DTO.setAccesses(ACCESS_DTO);
    }

    public static final CleverCustomer CLEVER_CUSTOMER = new CleverCustomer();

    static {
        CLEVER_CUSTOMER.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE.plusDays(5));
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(1);
        practice1.setRegisteredAppointmentDate(NOW_DATE.plusDays(2));
        practice1.setRegisteredAppointmentTime(NOW_TIME.minusHours(13));
        practice1.setRegisteredPaymentPromiseDate(NOW_DATE.plusDays(1));
        practice1.setLastRegisteredPromiseType(PracticeHesitationPaymentPromiseTypeEnum.PP);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.plusDays(6));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.plusDays(7));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER.setOpenPractices(practices);
    }

    /* Cliente per casi limite su date e orari */
    public static final CleverCustomer CLEVER_CUSTOMER_PROMISE_TODAY = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_PROMISE_TODAY.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(1);
        practice1.setRegisteredAppointmentDate(NOW_DATE);
        practice1.setRegisteredAppointmentTime(NOW_TIME.plusHours(2));
        practice1.setRegisteredPaymentPromiseDate(NOW_DATE);
        practice1.setLastRegisteredPromiseType(PracticeHesitationPaymentPromiseTypeEnum.PT);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_PROMISE_TODAY.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_PROMISE_PAST = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_PROMISE_PAST.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(1);
        practice1.setRegisteredAppointmentDate(NOW_DATE.minusDays(1));
        practice1.setRegisteredAppointmentTime(NOW_TIME.plusHours(2));
        practice1.setRegisteredPaymentPromiseDate(NOW_DATE.minusDays(1));
        practice1.setLastRegisteredPromiseType(PracticeHesitationPaymentPromiseTypeEnum.PP);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_PROMISE_PAST.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_PROMISE_PAST_ALREADY_PROG = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_PROMISE_PAST_ALREADY_PROG.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(2);
        practice1.setRegisteredAppointmentDate(NOW_DATE.minusDays(1));
        practice1.setRegisteredAppointmentTime(NOW_TIME.plusHours(2));
        practice1.setRegisteredPaymentPromiseDate(NOW_DATE.minusDays(1));
        practice1.setLastRegisteredPromiseType(PracticeHesitationPaymentPromiseTypeEnum.PP);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_PROMISE_PAST_ALREADY_PROG.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_APPOINTMENT_FUTURE = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_APPOINTMENT_FUTURE.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(0);
        practice1.setRegisteredAppointmentDate(NOW_DATE.plusDays(1));
        practice1.setRegisteredAppointmentTime(NOW_TIME.plusHours(2));
        practice1.setRegisteredPaymentPromiseDate(null);
        practice1.setLastRegisteredPromiseType(null);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_APPOINTMENT_FUTURE.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_APPOINTMENT_TODAY = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_APPOINTMENT_TODAY.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(0);
        practice1.setRegisteredAppointmentDate(NOW_DATE);
        practice1.setRegisteredAppointmentTime(NOW_TIME.plusHours(2));
        practice1.setRegisteredPaymentPromiseDate(null);
        practice1.setLastRegisteredPromiseType(null);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_APPOINTMENT_TODAY.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_APPOINTMENT_PAST = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_APPOINTMENT_PAST.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(0);
        practice1.setRegisteredAppointmentDate(NOW_DATE);
        practice1.setRegisteredAppointmentTime(NOW_TIME.minusHours(2));
        practice1.setRegisteredPaymentPromiseDate(null);
        practice1.setLastRegisteredPromiseType(null);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_APPOINTMENT_PAST.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_MULTI_PROMISE_PAST = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_MULTI_PROMISE_PAST.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(1);
        practice1.setRegisteredAppointmentDate(NOW_DATE.minusDays(1));
        practice1.setRegisteredAppointmentTime(NOW_TIME.plusHours(2));
        practice1.setRegisteredPaymentPromiseDate(NOW_DATE.minusDays(1));
        practice1.setLastRegisteredPromiseType(null);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_MULTI_PROMISE_PAST.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_MULTI_APPOINTMENT_PAST = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_MULTI_APPOINTMENT_PAST.setMultiPractice(true);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(2);
        practice1.setTotalRegisteredPromises(0);
        practice1.setRegisteredAppointmentDate(NOW_DATE);
        practice1.setRegisteredAppointmentTime(NOW_TIME.minusHours(2));
        practice1.setRegisteredPaymentPromiseDate(null);
        practice1.setLastRegisteredPromiseType(null);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_MULTI_APPOINTMENT_PAST.setOpenPractices(practices);
    }


    public static final CleverCustomer CLEVER_CUSTOMER_NO_APPOINTMENT_NO_PROMISES = new CleverCustomer();

    static {
        CLEVER_CUSTOMER_NO_APPOINTMENT_NO_PROMISES.setMultiPractice(false);
        List<CleverPractice> practices = new LinkedList<>();
        CleverPractice practice1 = new CleverPractice();
        practice1.setBankBranchNumber("234565");
        practice1.setBankBranchAddress("Torino");
        practice1.setStartDate(NOW_DATE.minusMonths(2));
        practice1.setExpiryDate(NOW_DATE);
        practice1.setHasEmail(Boolean.TRUE);
        practice1.setHasAtLeastOne1100Product(Boolean.FALSE);
        practice1.setTotalRegisteredAppointments(0);
        practice1.setTotalRegisteredPromises(0);
        practice1.setRegisteredAppointmentDate(null);
        practice1.setRegisteredAppointmentTime(null);
        practice1.setRegisteredPaymentPromiseDate(null);
        practice1.setLastRegisteredPromiseType(null);
        List<CleverProduct> outstandings = new LinkedList<>();
        List<CleverProduct> installments = new LinkedList<>();
        CleverProduct cleverProduct1 = new CleverProduct(); // installments
        cleverProduct1.setProductId("AB12");
        cleverProduct1.setProductType("p1");
        cleverProduct1.setOverdueInstallments(5);
        cleverProduct1.setOverdraftAmount(1111.1);
        cleverProduct1.setOverdraftDate(NOW_DATE.minusMonths(2));
        cleverProduct1.setTotalOverdraft(1222.2);
        cleverProduct1.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct1);
        CleverProduct cleverProduct2 = new CleverProduct(); // outstanding
        cleverProduct2.setProductId("AB21");
        cleverProduct2.setProductType("P2");
        //cleverProduct2.setOutstanding(2222.2);
        cleverProduct2.setTotalOverdraft(2333.3);
        cleverProduct2.setProduct1100(Boolean.FALSE);
        outstandings.add(cleverProduct2);
        CleverProduct cleverProduct3 = new CleverProduct(); // installments
        cleverProduct3.setProductId("AB13");
        cleverProduct3.setProductType("P1");
        cleverProduct3.setOverdueInstallments(1);
        cleverProduct3.setOverdraftAmount(3333.3);
        cleverProduct3.setOverdraftDate(NOW_DATE.minusMonths(2).plusDays(1));
        cleverProduct3.setTotalOverdraft(3444.4);
        cleverProduct3.setProduct1100(Boolean.FALSE);
        installments.add(cleverProduct3);
        practice1.setInstallmentsProducts(installments);
        practice1.setNotInstallmentsProducts(outstandings);
        practice1.setMaxAmountOwed(3444.4);
        practice1.setTotalAmountOwed(6999.9);
        practices.add(practice1);
        CLEVER_CUSTOMER_NO_APPOINTMENT_NO_PROMISES.setOpenPractices(practices);
    }
}
