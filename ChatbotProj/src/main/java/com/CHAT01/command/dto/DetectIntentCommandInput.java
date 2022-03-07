package com.CHAT01.command.dto;


import com.CHAT01.connector.dlp.google.token.model.ChannelEnum;
import lombok.Data;
import model.Customer;
import model.Event;
import model.FrontEndInputParameters;



@Data
public class DetectIntentCommandInput {
    private String sessionId;
    private Customer customer;
    private ChannelEnum channel;
// private Chat chat;


  private Event event;
  private FrontEndInputParameters frontEndInputParameters;
    private String  text;

}
