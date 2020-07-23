package it.dstech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.dstech.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    Authority findByName(String name);

}