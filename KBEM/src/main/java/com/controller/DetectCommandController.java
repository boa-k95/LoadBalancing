package controller;

import factory.createInputCommandFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("detect")
public class DetectCommandController {

    private final Logger logger = LoggerFactory.getLogger(DetectCommandController.class);
    @Autowired private BeanFactory  beanFactory;
    @Autowired private createInputCommandFactory factory;

    @GetMapping("/create")
    public String getStudent(@RequestParam(required = false) String registrationNumber){

        /*CommandInput input1 = factory.createInputCommandElement(registrationNumber);
        StudentCommand command = beanFactory.getBean(StudentCommand.class,input1);
        String stringToReturn = command.execute();
*/
      return registrationNumber.toUpperCase();

    }
}
