package jdanimal.demo.data.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAnimalUploadDTO {

    private String nameOfAnimal;
    private String typeOfAnimal;
    private String ageOfAnimal;
    private String gamesOfAnimal;
    private String foodOfAnimal;
    private String kilogramsOfAnimal;
    private String availableTill;
    private String urlAnimalPhoto;
    private String description;

}
