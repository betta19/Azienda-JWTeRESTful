package it.dstech.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.dstech.model.Servizio;
import it.dstech.repository.ServizioRepository;

@Service
public class ServizioDAOImpl {

	@Autowired
	ServizioRepository servizioRepo;

	public boolean salvaServizio(Servizio servizio) {
		Servizio s = servizioRepo.findByTipologia(servizio.getTipologia());
		if (s != null) {
			return false;
		}
		servizioRepo.save(servizio);

		return true;
	}
	
	
}
