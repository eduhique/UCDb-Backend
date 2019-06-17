package psoft.backend.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import psoft.backend.model.User;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, String> {

    User save(User user);

    @Query(value = "Select u from User as u where u.email=:pEmail")
    User findByEmail(@Param("pEmail") String email);

    List<User> findAll();

}
