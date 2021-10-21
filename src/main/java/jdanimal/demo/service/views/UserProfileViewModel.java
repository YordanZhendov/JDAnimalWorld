package jdanimal.demo.service.views;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import jdanimal.demo.data.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileViewModel {

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    private String urlProfilePicture;
    private Set<Animal> likedAnimals;
    private Set<Accessory> likedAccessories;
    private Set<Role> authorities;
}
