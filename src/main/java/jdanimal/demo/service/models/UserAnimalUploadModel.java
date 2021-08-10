package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnimalUploadModel {

    @NotBlank(message = "Field must be filled!")
    @Length(min = 3,message = "Name's length must be greater than 2")
    private String nameOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String typeOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String ageOfAnimal;
    private String gamesOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String foodOfAnimal;
    @NotBlank(message = "Field must be filled!")
    private String kilogramsOfAnimal;
    private String availableTill;


}
