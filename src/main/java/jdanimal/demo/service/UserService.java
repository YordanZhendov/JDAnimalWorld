package jdanimal.demo.service;


import jdanimal.demo.data.DTO.UserLoginDTO;
import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.web.models.UserProfileModel;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.ModelAndView;

public interface UserService extends UserDetailsService {
    void register(UserRegistrationDTO userRegistrationDTO);
    User validUser(UserLoginDTO userLoginDTO);
    UserProfileModel findByUsername(String currentUserName);
}
