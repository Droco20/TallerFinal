package com.sofka.dao;


import com.sofka.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/** Permite el acceso a la base de datos*/


public interface UserDao extends CrudRepository<User, Long>  {

    @Modifying
    @Query("update User cont set cont.name = :name where cont.id =:id")
    void updateName(
            @Param(value = "id") Long id,
            @Param(value = "name") String name
    );


    @Modifying
    @Query("update User cont set cont.email = :email where cont.id =:id")
    void updateEmail(
            @Param(value = "id") Long id,
            @Param(value = "email") String email
    );
}
