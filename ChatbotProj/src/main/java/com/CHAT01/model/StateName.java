package com.CHAT01.model;

import  java.util.HashMap;
import java.util.Map;

public enum StateName {

    /* StateName comuni ai canali (APP,IB) */
    NUMERIUTILI("numeriutili"),
    ARCHIVIO("archivio"),
    AVVISI("avvisi"),
    HOMEPAGE("homepage"),
    UNIFIEDADDRESSBOOK("unifiedaddressbook"),
    RICORDAMI("ricordami"),
    PULSE("pulse"),

    /* StateName APP */
    APP_PATRIMONIO("patrimonio"),
    APP_TRADING("trading"),
    CARDS("cards"),
    BLOCCACARTE("bloccacarte"),
    OPERATIONS_PAYMENT("operations_payment"),
    NFC("nfc"),
    BANCOMAT_PAY("bancomat_pay"),
    MONEYTRANSFERS("moneytransfers"),
    PAYMENTS("payments"),
    RECHARGE("recharge"),
    TAXES("taxes"),
    ATM_SMART("atm_smart"),
    PRELIEVO_EMERGENZA("prelievo_emergenza"),
    REVOKES("revokes"),
    OP_RICORRENTI("op_ricorrenti"),
    DOMICILIAZIONI("domiciliazioni"),
    GESTOREDEDICATO("gestorededicato"),
    ASSISTENZA("assistenza"),
    FILIALE("filiale"),
    F24ORDINARIO("f24ordinario"),
    EMAIL("email"),
    APPUNTAMENTI("appuntamenti"),
    NUM_UTILI("num_utili"),
    GUIDE("guide"),
    TAB_ALTRO("tab_altro"),
    CARRELLO("carrello"),
    TIPS("tips"),
    MYINVESTMENTSSAVINGSANDWELFARE("myinvestmentssavingsandwelfare"),
    LOANS("loans"),
    I_MIEI_FIGLI("i_miei_figli"),
    PRODUCTS("products"),
    REWARD("reward"),
    REWARD_LOYALTY("reward_loyalty"),
    EXCLUSIVE("exclusive"),
    DIGITAL_WALLET("digital_wallet"),
    XME_BANKS("xme_banks"),
    XMESALUTE("xmesalute"),
    SCAN("scan"),
    MYPROFILE("myprofile"),
    MYBANKS("mybanks"),
    SETTINGS("settings"),
    OKEYSMARTACTIVATION("okeysmartactivation"),
    NOTIFICATIONS("notifications"),
    NOTIFICATIONS_V2("notifications_v2"),
    DEVICES_MANAGEMENT("devices_management"),
    PRELOGIN("prelogin"),
    PAYGO("paygo"),
    BANCOMAT_PAY_SETTINGS("bancomat_pay_settings"),
    PRIVACY("privacy"),
    COOKIE("cookie"),
    MASTERPASS_SETTINGS("masterpass_settings"),
    DIGITAL_WALLET_SETTINGS("digital_wallet_settings"),
    SPID("spid"),
    VICINOAME("vicinoame"),
    EXIT("exit"),
    BOLLOAUTO("bolloauto"),

    /* StateName IB */
    PARLACONNOI("parlaconnoi"),
    CARD("card"),
    PREBULLETTIN("prebullettin"),
    CARTAX("cartax"),
    PROFILEHOME("profilehome"),
    SECURITYANDSIGN("securityandsign"),
    MAVRAV("mavrav"),
    OPERATIONS_OPERAZIONIPIANIFICATE("operations.operazionipianificate"),
    CBILL("cbill"),
    PATRIMONIO("patrimonio"),
    PRIVACYMYPROFILE("privacymyprofile"),
    REVOCATION("revocation"),
    IMIEIPUNTI("imieipunti"),
    CARDRECHARGE("cardrecharge"),
    CREDENZIALISPID("credenzialispid"),
    STANDINGORDERS("standingorders"),
    TRANSFER("transfer"),
    TRANSFERINT("transferint"),
    SUBSCRIPTIONTRANSPORT("subscriptiontransport"),
    EQUITALIA("equitalia"),
    CHECKSACCOUNTS("checksaccounts"),
    WHITEBULLETTIN("whitebullettin"),
    VIRTUALCARDS("virtualcards"),
    ALERTMYPROFILE("alertmyprofile"),
    INFOGRAPHIC("infographic"),
    MYACCOUNTS("myaccounts"),
    IMIEIFIGLI("imieifigli"),
    MORTGAGES("mortgages"),
    MYAIMSPFM("myaimspfm"),
    WALLETDETTAGLIOPOLIZZA("walletdettagliopolizza"),
    MASTERPASSWALLETDIGITALPURSE("masterpasswalletdigitalpurse"),
    FINES("fines"),
    UTILIZZAILTUOPRESTITO("utilizzailtuoprestito"),
    RIBA("riba"),
    SIMULATOREMUTUI("simulatoremutui"),
    TRANSFERWU("transferwu"),
    PRODUCTSSETTINGS("productssettings"),
    F24("f24"),
    MANAGEDEVICE("managedevice"),
    GIROTRANSFER("girotransfer"),
    MYCARDS("mycards"),
    MYCOMPANIES("mycompanies"),
    MOBILES("mobiles"),
    UNIVERSITYTAXES("universitytaxes"),
    BANKENROLLMENT("bank-enrollment-process.bank-enrollment-process"),
    PAYMENTESFA("payments-fa.payments-fa"),
    TRANSFERITA("transferita"),
    INCAPSCONTAINER("incapscontainer");

    private static final Map<String, StateName> reverseMap = new HashMap<>();

    static {
        for (StateName stateName : StateName.values()) {
            reverseMap.put(stateName.getStateName(), stateName);
        }
    }

    private String name;

    StateName(String stateName) {
        name = stateName;
    }

    /**
     * Restituisce una mappa avente come chiave la
     * stringa ricevuta in input e come valore lo stateName
     *
     * @return mappa
     */
    public static Map<String, StateName> getReverseMap() {
        return reverseMap;
    }

    /**
     * Restituisce una singola stringa di stateName
     *
     * @return Stringa StateName
     */
    public String getStateName() {
        return name;
    }

    public static StateName getStateNameByString(String stateName) {
        for (StateName s : StateName.values()) {
            if (s.getStateName().equals(stateName))
                return s;
        }
        return null;
    }
}
