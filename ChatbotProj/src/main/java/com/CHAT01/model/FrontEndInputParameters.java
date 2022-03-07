package com.CHAT01.model;

import lombok.Data;

import java.util.List;

@Data // Serve per test
public class FrontEndInputParameters {
    private String type;
    private List<? super Object> values;
}