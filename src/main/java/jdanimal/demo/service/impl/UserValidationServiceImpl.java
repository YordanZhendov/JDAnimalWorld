package jdanimal.demo.service.impl;

import jdanimal.demo.data.User;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.UserValidationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserValidationServiceImpl implements UserValidationService {

    private final UserRepository userRepository;

    private boolean emailValid(String email) {
        return !userRepository.existsByEmail(email);
    }

    private boolean phoneCheck(String phonenumber) {
        return !userRepository.existsByPhoneNumber(phonenumber);
    }

    private boolean usernameFree(String username) {
        return !userRepository.existsByUsername(username);
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean emailOrPhoneNumberTaken(String email, String phoneNumber,String userName){
        User byUsername = userRepository.findByUsername(userName);
        boolean existsByPhoneNumber = userRepository.existsByPhoneNumber(phoneNumber);
        boolean existsByEmail = userRepository.existsByEmail(email);
        System.out.println("werwr");

        if(byUsername.getPhoneNumber().equals(phoneNumber) && byUsername.getEmail().equals(email)){
            return false;
        }

        if(byUsername.getEmail().equals(email) && existsByPhoneNumber){
            return true;
        }
        if(byUsername.getPhoneNumber().equals(phoneNumber) && existsByEmail){
            return true;
        }
        if(byUsername.getEmail().equals(email) && !existsByPhoneNumber){
            return false;
        }
        return !byUsername.getPhoneNumber().equals(phoneNumber) || existsByEmail;
    }

    @Override
    public boolean checkUser(User user) {
        return this.isPasswordValid(user.getPassword(),user.getConfirmPassword()) &&
                this.usernameFree(user.getUsername()) &&
                this.emailValid(user.getEmail()) && this.phoneCheck(user.getPhoneNumber());
    }

    @Override
    public boolean checkDetails(User user){
        return this.emailOrPhoneNumberTaken(user.getEmail(), user.getPhoneNumber(),user.getUsername());
    }

}
