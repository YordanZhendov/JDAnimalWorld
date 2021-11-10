package jdanimal.demo.service.views;

import jdanimal.demo.data.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalViewModel {

    private String id;
    private String animalPicture;
    private String nameOfAnimal;
    private String typeOfAnimal;
    private Integer ageOfAnimal;
    private String gamesOfAnimal;
    private String foodOfAnimal;
    private Integer kilogramsOfAnimal;
    private String availableTill;
    private String urlAnimalPhoto;
    private String description;
    private User user;
}
