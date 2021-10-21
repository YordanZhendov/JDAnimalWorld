package jdanimal.demo.data.DTO;

import jdanimal.demo.data.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncyclopediaAnimalBinding {

    @NotNull(message = "Category needed!")
    private AnimalType typeOfAnimal;
    @NotEmpty(message = "Description needed!")
    private String description;
    @NotEmpty(message = "Photo Url needed!")
    private String animalPhotoUrl;
}
