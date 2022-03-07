package command;

import factory.createStudentFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class StudentCommand implements BaseCommand<String> {
    Logger logger = LoggerFactory.getLogger(StudentCommand.class);
     private  CommandInput input;

    @Override
    public String execute() {
      String gettingString = input.getRegistrationNumber();
      return gettingString.concat("&&&&&&&&&&");

    }
}
