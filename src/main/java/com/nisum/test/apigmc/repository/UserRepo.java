package com.nisum.test.apigmc.repository;

import com.nisum.test.apigmc.domain.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findOneByEmail(String email);

    @Query("select u from User u left join fetch u.phones")
    List<User> findAllUsers();
}
