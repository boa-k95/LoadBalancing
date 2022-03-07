package com.CHAT01.model;

import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data // Serve per test
public class OutputParameters {
    private String type;
    private List<? extends Object> values;
    private ChannelEnum channel;
   // private CustomerTypeEnum customerTypeEnum;

    public OutputParameters() {
      //  this.type = ResponseTypeEnum.NONE.name();
        this.values = Collections.emptyList();
        this.channel = null;
       // this.customerTypeEnum = null;
    }
}