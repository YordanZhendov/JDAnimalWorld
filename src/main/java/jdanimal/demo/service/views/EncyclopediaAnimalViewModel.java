package jdanimal.demo.service.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EncyclopediaAnimalViewModel {

    private String id;
    private String typeOfAnimal;
    private String description;
    private String animalPhotoUrl;
}
