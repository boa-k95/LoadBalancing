package com.CHAT01.controller.intent.dto;

import lombok.Data;
import model.CustomerTypeEnum;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
public class CustomerDTO {
    @NotNull
    private CustomerTypeEnum type;
    @NotNull
//    @NotEmpty
    private String abi;
    @NotNull
//    @NotEmpty
    private String bt;
    @NotNull
    private String nsg;
    @NotNull
    private String taxCode;

    private String businessBt;
    @NotNull
    private List<String> accounts;
    @NotNull
    @NotEmpty
    private Map<String, Boolean> accesses;
}
