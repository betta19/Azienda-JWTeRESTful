package it.dstech.memento;

import it.dstech.model.Servizio;


public class Memento {

	private Servizio servizio;

	public Memento(Servizio servizio) {
	this.servizio = servizio;
}

	public Servizio getServizio() {
		return servizio;
	}

	public void setServizio(Servizio servizio) {
		this.servizio = servizio;
	}
	
//	public void restore(Memento objMemento) {
//		Memento memento = (Memento) objMemento;
//		tipologia = memento.mementoTipologia;
//		qtaDisp = memento.mementoQtaDisp;
//		qtaTot = memento.mementoQtaTot;
//		users = memento.mementoUsers;
//	}

	
}
