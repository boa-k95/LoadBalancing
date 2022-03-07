package com.CHAT01.model;

import lombok.Data;

@Data // Serve per test
public class Chat {
    private String type;
    private String room;
    private String operator;
}
