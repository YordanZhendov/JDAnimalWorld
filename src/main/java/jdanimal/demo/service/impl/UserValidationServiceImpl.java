package jdanimal.demo.service.impl;

import jdanimal.demo.data.User;
import jdanimal.demo.repository.UserRepository;
import jdanimal.demo.service.UserValidationSerivce;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserValidationServiceImpl implements UserValidationSerivce {

    private final UserRepository userRepository;

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
