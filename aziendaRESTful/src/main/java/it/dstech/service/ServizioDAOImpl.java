package it.dstech.service;

import java.util.ArrayList;
import java.util.List;

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

	public void salva(Servizio servizio) {

		servizioRepo.save(servizio);

	}

	public List<Servizio> findAll() {
		return servizioRepo.findAll();
	}
	
	public Servizio findServizio(String tipologia) {
		return servizioRepo.findByTipologia(tipologia);
	}

	public List<Servizio> findServiziDisp() {
		List<Servizio> list = new ArrayList<Servizio>();
		for (Servizio servizio : servizioRepo.findAll()) {
			if (servizio.getQtaDisp() > 0) {
				list.add(servizio);
			}
		}
		return list;
	}

}
