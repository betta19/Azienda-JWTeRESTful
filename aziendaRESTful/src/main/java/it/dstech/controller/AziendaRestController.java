package it.dstech.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import it.dstech.memento.Memento;
import it.dstech.memento.ServizioCaretaker;
import it.dstech.model.Servizio;
import it.dstech.security.JwtAuthenticationRequest;
import it.dstech.security.JwtTokenUtil;
import it.dstech.service.JwtAuthenticationResponse;
import it.dstech.service.ServizioDAOImpl;

@RestController
public class AziendaRestController {

	@Value("${jwt.header}")
	private String tokenHeader;

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return new AuthenticationManager() {

			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				return null;
			}
		};
	}

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private ServizioDAOImpl service;
	
	@Autowired
	private ServizioCaretaker careTaker;

	@RequestMapping(value = "public/login", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest,
			HttpServletResponse response) throws Exception {

		AuthenticationManager authenticationManager = authenticationManagerBean();
		// Effettuo l autenticazione
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
						authenticationRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Genero Token
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		response.setHeader(tokenHeader, token);
		// Ritorno il token
		return ResponseEntity
				.ok(new JwtAuthenticationResponse(userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@RequestMapping(value = "protected/refresh-token", method = RequestMethod.GET)
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request,
			HttpServletResponse response) {
		String token = request.getHeader(tokenHeader);
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			String refreshedToken = jwtTokenUtil.refreshToken(token);
			response.setHeader(tokenHeader, refreshedToken);

			return ResponseEntity
					.ok(new JwtAuthenticationResponse(userDetails.getUsername(), userDetails.getAuthorities()));
		} else {
			return ResponseEntity.badRequest().body(null);
		}
	}

	@RequestMapping(value = "protected/aggiungiServizio", method = RequestMethod.POST)
	public boolean aggiungiServizio(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Servizio servizio) {
		String token = request.getHeader(tokenHeader);
		UserDetails userDetails = jwtTokenUtil.getUserDetails(token);
		
		if (userDetails.getAuthorities().contains(new SimpleGrantedAuthority("AZIENDA"))) {
			service.salvaServizio(servizio);
			careTaker.getMementos().add(new Memento(servizio));
			return true;
		}
		return false;
	}

	@RequestMapping(value = "protected/listaServizi", method = RequestMethod.POST)
	public List<Servizio> listaServizi(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(tokenHeader);
		UserDetails userDetails = jwtTokenUtil.getUserDetails(token);

		if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("AZIENDA"))) {
			return null;
		}
		return service.findAll();
	}

	@RequestMapping(value = "protected/serviziDisponibili", method = RequestMethod.POST)
	public List<Servizio> listaServiziDisponibili(HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader(tokenHeader);
		UserDetails userDetails = jwtTokenUtil.getUserDetails(token);

		if (!userDetails.getAuthorities().contains(new SimpleGrantedAuthority("DIPENDENTE"))) {
			return null;
		}
		return service.findServiziDisp();
	}

}
