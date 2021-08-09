package jdanimal.demo.service.impl;

import jdanimal.demo.data.DTO.UserRegistrationDTO;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.UserValidationSerivce;
import org.springframework.stereotype.Service;

@Service
public class UserValidationServiceImpl implements UserValidationSerivce {

    private final UserRepository userRepository;

    public UserValidationServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    private boolean emailValid(String email) {
        return !userRepository.existsByEmail(email);
    }

    private boolean usernameFree(String username) {
        return !userRepository.existsByUsername(username);
    }

    private boolean isPasswordValid(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    @Override
    public boolean checkUser(User user) {
        return this.isPasswordValid(user.getPassword(),user.getConfirmPassword()) &&
                this.usernameFree(user.getUsername()) &&
                this.emailValid(user.getEmail());
    }
}
