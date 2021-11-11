package jdanimal.demo.web.binding;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStoreUploadBinding {

    @NotBlank(message = "Field must be filled!")
    private String name;
    @NotBlank(message = "Field must be filled!")
    @Pattern(regexp = "<iframe src=\"(?:[^\\\\\"]|\\\\\\\\|\\\\\")*\" width=\"600\" height=\"450\" style=\"border:0;\" allowfullscreen=\"\" loading=\"lazy\"></iframe>", message = "Please enter the correct URL!")
    private String locationPath;
    private String startHour;
    private String endHour;
    private String description;

}
