package jdanimal.demo.web.api;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.Store;
import jdanimal.demo.data.User;
import jdanimal.demo.repository.AccessoryRepository;
import jdanimal.demo.repository.AnimalRepository;
import jdanimal.demo.repository.StoreRepository;
import jdanimal.demo.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@CrossOrigin("http://localhost:3000")
@RestController
@AllArgsConstructor
public class APIController {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final AccessoryRepository accessoryRepository;
    private final StoreRepository storeRepository;

    @GetMapping("/api/{username}")
    public ResponseEntity<User> userByUserName(@PathVariable(name = "username") String username){
        return new ResponseEntity<>(userRepository.findByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/api/allusers")
    public ResponseEntity<List<User>> allUsers(){
        return new ResponseEntity<>(userRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/allanimals")
    public ResponseEntity<List<Animal>> allAnimals(){
        return new ResponseEntity<>(animalRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/allaccessories")
    public ResponseEntity<List<Accessory>> allAccessories(){
        return new ResponseEntity<>(accessoryRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping("/api/stores")
    public ResponseEntity<List<Store>> allStores(){
        return new ResponseEntity<>(storeRepository.findAll(), HttpStatus.OK);
    }

}
