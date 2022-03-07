package com.CHAT01.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data // Serve per test
public class Customer {
    private CustomerTypeEnum type;
    private String abi;
    private String bt;
    private String nsg;
    private List<String> accounts;
    private String taxCode;
    private String businessBt;
    private Set<StateName> accesses;
}
