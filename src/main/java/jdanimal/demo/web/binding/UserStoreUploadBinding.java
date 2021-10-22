package jdanimal.demo.web.binding;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.sql.Time;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
