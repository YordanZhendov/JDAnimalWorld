package jdanimal.demo.web.binding;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAnimalUploadBinding {

    @Length(min = 3,message = "Name's length must be greater than 2")
    @NotBlank(message = "Field must be filled!")
    private String nameOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String typeOfAnimal;
    @NotNull(message = "Field must be filled!")
    private Integer ageOfAnimal;
    private String gamesOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String foodOfAnimal;
    @NotNull(message = "Field must be filled!")
    private Integer kilogramsOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String availableTill;
    private String urlAnimalPhoto;
    private String description;

}
