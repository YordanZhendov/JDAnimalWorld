package jdanimal.demo.repository;

import jdanimal.demo.data.Accessory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory,String> {

    @Query("select a from Accessory as a")
    List<Accessory> getAccessories();
}
