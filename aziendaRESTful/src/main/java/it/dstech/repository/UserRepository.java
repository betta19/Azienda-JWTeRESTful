package it.dstech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);

}
