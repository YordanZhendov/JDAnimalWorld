package jdanimal.demo.service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncyclopediaAnimalUploadModel {

    private String typeOfAnimal;
    private String description;
    private String animalPhotoUrl;
}
