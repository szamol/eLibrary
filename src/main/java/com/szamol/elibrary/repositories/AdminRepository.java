package com.szamol.elibrary.repositories;


import com.szamol.elibrary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM users u WHERE u.first_name LIKE %:exp% OR u.last_name LIKE %:exp% OR u.email LIKE %:exp%", nativeQuery = true)
    List<User> searchUser(@Param("exp") String exp);

    @Modifying
    @Query(value = "UPDATE user_role ur SET ur.role_id = :roleId where ur.user_id = :id", nativeQuery = true)
    void changeRole(@Param("id") int id, @Param("roleId") int roleId);
}
