package jdanimal.demo.web.binding;

import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAccessoryUploadBinding {

    @NotBlank(message = "Field must be filled!")
    private String accessoryName;
    @NotBlank(message = "Field must be filled!")
    private String availableTill;
    @Min(0)
    private int daysUsed;
    @NotNull
    @Positive
    private BigDecimal accessoryPrice;
    private String description;
}
