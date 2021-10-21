package jdanimal.demo.data;

import jdanimal.demo.data.enums.AnimalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "encyclopedia")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EncyclopediaAnimal extends BaseEntity{

    @Enumerated(EnumType.STRING)
    @Column(name = "type_of_animal")
    private AnimalType typeOfAnimal;
    @Column(name = "description")
    @Type(type="text")
    private String description;
    @Column(name = "animal_photo_url")
    private String animalPhotoUrl;
}
