package it.dstech.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import it.dstech.model.Servizio;
import it.dstech.model.User;
import it.dstech.repository.ServizioRepository;
import it.dstech.repository.UserRepository;
import it.dstech.security.JwtUserFactory;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private ServizioRepository servizioRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", email));
        } else {
            return JwtUserFactory.create(user);
        }
    }
    
    public List<User> findAll(){
    	return userRepository.findAll();
    }
    
	public void salvaUtente(User u) {

		userRepository.save(u);

	}
	
	public User cercaUtente(String email) {

		return userRepository.findByEmail(email);

	}

	public boolean aggiungiServizio(User utente, Servizio servizio) {
		
		if(utente.getCount() == 0) {
		
			return false;
		}	
		utente.setCount(utente.getCount()-1);
		utente.getServizio().add(servizio);
		
		servizio.setQtaDisp(servizio.getQtaDisp()-1);
		servizio.getUsers().add(utente);
		
		salvaUtente(utente);
		servizioRepo.save(servizio);
		
		return true;
		
	}
}