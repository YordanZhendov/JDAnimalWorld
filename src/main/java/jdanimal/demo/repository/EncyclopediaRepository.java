package jdanimal.demo.repository;

import jdanimal.demo.data.EncyclopediaAnimal;
import jdanimal.demo.data.enums.AnimalType;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncyclopediaRepository extends JpaRepository<EncyclopediaAnimal,String> {

    @Cacheable("encyclopedia")
    @Query("SELECT a from EncyclopediaAnimal as a")
    List<EncyclopediaAnimal> getAllAnimals();

    @CachePut("encyclopedia")
    List<EncyclopediaAnimal> findAll();

    List<EncyclopediaAnimal> getAllByTypeOfAnimal(AnimalType typeOfAnimal);
}
