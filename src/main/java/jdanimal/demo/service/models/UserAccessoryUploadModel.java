package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserAccessoryUploadModel {

    @NotBlank(message = "Field must be filled!")
    private String accessoryName;
    @NotBlank(message = "Field must be filled!")
    private String availableTill;
    @Min(0)
    private int daysUsed;
    @Min(0)
    @NotNull
    private BigDecimal accessoryPrice;
    private String description;
}
