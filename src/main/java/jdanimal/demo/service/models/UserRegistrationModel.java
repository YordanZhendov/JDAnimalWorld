package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationModel {

    @NotBlank(message = "Fields must be filled!")
    @Length(min = 3,message = "Symbols must be more than 2!")
    private String username;
    private String fullName;
    @Length(min = 5,message = "Password must has minimum 5 symbols!")
    private String password;
    @Length(min = 5,message = "Password must has minimum 5 symbols!")
    private String confirmPassword;
    @NotBlank()
    @Email()
    private String email;
    @Length(min = 9,max = 9,message = "Enter your 9 digits!")
    private String phoneNumber;
    private String country;
    private String city;
    @Length(min = 4,max = 6,message = "Postcode must be between 4-6 symbols (inclusive) ! ")
    private String postcode;
    private boolean policyAgree;

}
