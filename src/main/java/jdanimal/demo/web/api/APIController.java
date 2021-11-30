package jdanimal.demo.web.api;

import jdanimal.demo.data.User;
import jdanimal.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@AllArgsConstructor
public class APIController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @CrossOrigin(origins = "https://yordanzhendov.github.io")
    @GetMapping("/api/{username}")
    public ResponseEntity<User> userByUserName(@PathVariable(name = "username") String username){
        return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
    }
}
