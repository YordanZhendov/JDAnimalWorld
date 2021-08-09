package jdanimal.demo.service;
import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.data.User;

public interface UserValidationSerivce {
    boolean checkUser(User user);

}
