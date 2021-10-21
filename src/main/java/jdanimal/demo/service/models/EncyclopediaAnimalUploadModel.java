package jdanimal.demo.service.models;

import jdanimal.demo.data.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncyclopediaAnimalUploadModel {

    private AnimalType typeOfAnimal;
    private String description;
    private String animalPhotoUrl;
}
