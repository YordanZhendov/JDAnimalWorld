package jdanimal.demo.repository;

import jdanimal.demo.data.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,String> {

    @Query("SELECT a from Animal as a")
    List<Animal> findAllAnimals();

    @Query("select a from Animal as a WHERE a.user.username=?1")
    List<Animal> getAnimalByUser(String username);
    Animal findAnimalById(String id);

    @Query("select a from Animal as a WHERE a.user.id=?1")
    List<Animal> getAnimalByUserId(String id);

}
