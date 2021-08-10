package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileModel {

    private String username;
    private String fullName;
    private String password;
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
}
