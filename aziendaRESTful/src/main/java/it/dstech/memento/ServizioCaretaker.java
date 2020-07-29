package it.dstech.memento;

import java.util.ArrayList;
import java.util.List;

public class ServizioCaretaker {
	
	private List<Memento> mementos = new ArrayList<>();

	 public void addMemento(Memento m) {
	        mementos.add(m);
	    }

	public List<Memento> getMementos() {
		return mementos;
	}

	public void setMementos(List<Memento> mementos) {
		this.mementos = mementos;
	}
	 
	 
	
}

