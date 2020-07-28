package it.dstech.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "SERVIZIO")
public class Servizio {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "TIPOLOGIA", length = 100, unique = true)
	private String tipologia;

	@Column(name = "QTA_DISP")
	private int qtaDisp;

	@Column(name = "QTA_TOT")
	private int qtaTot;

	@ManyToMany(fetch = FetchType.LAZY)
	@JsonBackReference
	private List<User> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}

	public int getQtaDisp() {
		return qtaDisp;
	}

	public void setQtaDisp(int qtaDisp) {
		this.qtaDisp = qtaDisp;
	}

	public int getQtaTot() {
		return qtaTot;
	}

	public void setQtaTot(int qtaTot) {
		this.qtaTot = qtaTot;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
