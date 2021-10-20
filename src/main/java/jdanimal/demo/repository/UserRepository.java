package jdanimal.demo.repository;

import jdanimal.demo.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);

    @Query("select s from User as s")
    List<User>  getAllUsers();

    @Query("select s from User as s where s.authorities.size=1")
    List<User>  getAllUsersByAccessoryType();



}
