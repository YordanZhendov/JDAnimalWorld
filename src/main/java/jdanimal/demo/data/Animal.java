package jdanimal.demo.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "animals")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Animal extends BaseEntity{

    @Column(name = "name_of_animal")
    private String nameOfAnimal;
    @Column(name = "type_of_animal")
    private String typeOfAnimal;
    @Column(name = "age_of_animal")
    private String ageOfAnimal;
    @Column(name = "games_of_animal")
    private String gamesOfAnimal;
    @Column(name = "food_of_animal")
    private String foodOfAnimal;
    @Column(name = "kilograms_of_animal")
    private String kilogramsOfAnimal;
    @Column(name = "available_till")
    private String availableTill;
    @ManyToOne
    private User user;
}