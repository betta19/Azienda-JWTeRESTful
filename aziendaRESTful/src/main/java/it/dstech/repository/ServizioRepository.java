package it.dstech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.dstech.model.Servizio;

@Repository
public interface ServizioRepository extends JpaRepository<Servizio, Long>{

	Servizio findByTipologia (String tipologia);
	
	List<Servizio> findAll();
}
