package com.CHAT01.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CleverProduct {
    private String productId;
    private String productType; // Tipo prodotto ('05' sconfino su conto, altrimenti rateale)
    private Integer overdueInstallments;    // Numero rate arretrate
    private Double overdraftAmount; // Importo dovuto originale
    private LocalDate overdraftDate;    // inizio scofino
    private Double interestsOnArrears;    // interessi di mora
    private Double lateFees;    // interessi stragiudiziali
    private Double totalOverdraft;  // Importo dovuto totale
    private Double expensesAmount;  // Importo interessi
    private Boolean product1100;
    //private Double outstanding;
}