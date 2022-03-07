package com.CHAT01.factory;



import com.CHAT01.controller.intent.dto.CustomerDTO;
import com.CHAT01.controller.intent.dto.ParametersDTO;
import model.Customer;
import model.FrontEndInputParameters;
import model.StateName;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;

@Component
public class CommonFactory {
    private static final Logger logger = LoggerFactory.getLogger(CommonFactory.class);

    /*public Chat createChat(ChatDTO chatDTO) {
        Chat chat = new Chat();
        chat.setOperator(chatDTO.getOperator());
        chat.setRoom(chatDTO.getRoom());
        chat.setType(chatDTO.getType());
        return chat;
    }
*/
    public Customer createCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setAbi(customerDTO.getAbi());
        customer.setBt(customerDTO.getBt());
        customer.setBusinessBt(customerDTO.getBusinessBt());
        customer.setNsg(customerDTO.getNsg());
        customer.setAccounts(customerDTO.getAccounts());
        customer.setTaxCode(customerDTO.getTaxCode());
        customer.setType(customerDTO.getType());
        customer.setAccesses(new HashSet<>());
        for (Map.Entry<String, Boolean> access : customerDTO.getAccesses().entrySet()) {
            if (StateName.getReverseMap().containsKey(access.getKey()) && Boolean.TRUE.equals(access.getValue())) {
                customer.getAccesses().add(StateName.getReverseMap().get(access.getKey()));
            }
        }
        return customer;
    }

    public FrontEndInputParameters createInputParameters(ParametersDTO parametersDTO) {
        if (parametersDTO != null) {
            FrontEndInputParameters frontEndInputParameters = new FrontEndInputParameters();
            frontEndInputParameters.setType(parametersDTO.getType());
            frontEndInputParameters.setValues(parametersDTO.getValues());
            return frontEndInputParameters;
        } else {
            return null;
        }
    }
}
