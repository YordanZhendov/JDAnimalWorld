package jdanimal.demo.web.binding;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateProfileBinding {

    private String username;
    private String fullName;
    private String email;
    private String phoneNumber;

}