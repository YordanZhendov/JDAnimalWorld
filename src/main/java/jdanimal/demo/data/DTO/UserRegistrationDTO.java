package jdanimal.demo.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @Length(min = 3)
    private String username;
    private String fullName;
    @Length(min = 3)
    private String password;
    @Length(min = 3)
    private String confirmPassword;
    @Email
    private String email;
    private String phoneNumber;
    private String country;
    private String city;
    @Length(min = 4,max = 6)
    private String postcode;
    private boolean policyAgree;

}