package jdanimal.demo.data.DTO;


import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String urlProfilePicture;
    private Set<Animal> likedAnimals;
    private Set<Accessory> likedAccessories;

}
