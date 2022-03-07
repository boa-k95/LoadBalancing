package com.example.secondService.command;

import com.example.secondService.command.dto.customerDTO;
import com.example.secondService.factory.customerDTOfactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Scope("protoype")
@Component
@RequiredArgsConstructor
public class implementCommand extends Command<commandOutput> {

    @Autowired private  commandInput input;
     @Autowired private  BeanFactory beanFactory;

    @Override
    protected boolean canExecute() throws Exception {
        return true;
    }

    @Override
    protected commandOutput doExecute() throws Exception {
    commandOutput output = new commandOutput();
   ConsoleDLPCommand command = beanFactory.getBean(ConsoleDLPCommand.class, commandInput);
   ConsoleDLPCommandOutput commandOutput = command.execute();



        customerDTO factoryResponse = customerDTOfactory.getcreatecustomerDTO(input);

    }
}
