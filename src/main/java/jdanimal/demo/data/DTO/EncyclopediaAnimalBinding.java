package jdanimal.demo.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EncyclopediaAnimalBinding {

    @NotEmpty(message = "Type needed!")
    private String typeOfAnimal;
    @NotEmpty(message = "Description needed!")
    private String description;
    @NotEmpty(message = "Photo Url needed!")
    private String animalPhotoUrl;
}
