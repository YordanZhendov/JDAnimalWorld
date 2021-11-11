package jdanimal.demo.web.binding;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginBinding {

    private String username;
    private String password;
}
