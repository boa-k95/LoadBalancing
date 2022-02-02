package model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class customerModel {
    private String name;
    private String surname;
}
