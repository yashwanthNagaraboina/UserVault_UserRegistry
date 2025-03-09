package com.example.repo;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Custom method to find user by email
    Optional<User> findByEmail(String email);

    // Custom method to find users by role
    List<User> findByRole(String role);

    // Custom method for searching based on firstName, lastName, and ssn (free text search)
    List<User> findByFirstNameContainingOrLastNameContainingOrSsnContaining(String firstName, String lastName, String ssn);
}
