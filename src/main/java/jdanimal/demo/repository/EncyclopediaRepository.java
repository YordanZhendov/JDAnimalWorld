package jdanimal.demo.repository;

import jdanimal.demo.data.EncyclopediaAnimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EncyclopediaRepository extends JpaRepository<EncyclopediaAnimal,String> {

    List<EncyclopediaAnimal> findAll();
}
