package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;  
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
//     @Pattern(regexp="(?:[a-z0-9!#$%&'*+\/=?^_`{|}~-]+(?:\.[a-z0-9!#$%&'*+\/=?^_`{|}~-]+)*|"(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21\x23-\x5b\x5d-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])*")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\x01-\x08\x0b\x0c\x0e-\x1f\x21-\x5a\x53-\x7f]|\\[\x01-\x09\x0b\x0c\x0e-\x7f])+)\])
// ",message="Please enter a correct email address!")  
    @Email()
    private String email;
    @Length(min = 10,max = 10,message = "Enter your 10 digits!")
    private String phoneNumber;
    private String country;
    private String city;
    @Length(min = 4,max = 6,message = "Postcode must be between 4-6 symbols (inclusive) ! ")
    private String postcode;
    private boolean policyAgree;

}
