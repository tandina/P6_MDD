package com.mvp.P6mvpapi.repository;
import com.mvp.P6mvpapi.models.Role;
import com.mvp.P6mvpapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?1")
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);
    Optional<User> findByEmail(String email);

    User findByRole(Role role);

    Optional<User> findByUsername(String username);
}