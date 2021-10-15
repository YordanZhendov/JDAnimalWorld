package jdanimal.demo.repository;

import jdanimal.demo.data.Accessory;
import jdanimal.demo.data.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store,String> {

    @Query("select a from Store as a WHERE a.user.username=?1")
    List<Store> getStoreByUser(String username);

    @Query("select a from Store as a WHERE a.user.id=?1")
    List<Store> getStoreByUserId(String id);
}
