package jdanimal.demo.web.binding;

import jdanimal.demo.data.enums.AnimalType;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EncyclopediaAnimalBinding {

    @NotNull(message = "Category needed!")
    private AnimalType typeOfAnimal;
    @NotEmpty(message = "Description needed!")
    private String description;
    @NotEmpty(message = "Photo Url needed!")
    private String animalPhotoUrl;
}
