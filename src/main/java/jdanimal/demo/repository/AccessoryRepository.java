package jdanimal.demo.repository;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccessoryRepository extends JpaRepository<Accessory,String> {

    @Query("select a from Accessory as a")
    List<Accessory> getAccessories();

    @Query("select a from Accessory as a WHERE a.user.username=?1")
    List<Accessory> getAccessoriesByUser(String username);

    Accessory findAccessoryById(String id);

    @Query("select a from Accessory as a WHERE a.user.id=?1")
    List<Accessory> getAccessoryByUserId(String id);

}
