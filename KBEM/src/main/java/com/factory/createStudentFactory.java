package factory;


import model.Student;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class createStudentFactory {
 public  Student createStudentType(String registrationNumber) {
     Student stdFactory = new Student();

     if(StringUtils.isNotBlank(registrationNumber) || StringUtils.isNotEmpty(registrationNumber)){
         stdFactory.setSurname("boama");
         stdFactory.setName("kurtis");
         stdFactory.setAge(19);
    }
   else{
   return  null;
   }
   return stdFactory;
 }

}
