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
    private String nameOfAnimal;
    private String typeOfAnimal;
    private String ageOfAnimal;
    private String gamesOfAnimal;
    private String foodOfAnimal;
    private String kilogramsOfAnimal;
    private String availableTill;
    private User user;
}
