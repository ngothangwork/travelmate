package dev.thangngo.travelmate.repositories;

import dev.thangngo.travelmate.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> getUserById(UUID id);
}
