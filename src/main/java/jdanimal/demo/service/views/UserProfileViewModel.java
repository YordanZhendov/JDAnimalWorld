package jdanimal.demo.service.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
