package it.polimi.ingsw.ps23.model.actions;

import java.util.List;

public abstract class Action {
	
	private String name;
	
	public abstract void setParameters(List<String> parameters);

	public Action(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	@Override
	protected Object clone() {
		Object clone = null;
		try {
			clone = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
	      }
		return clone;
	}
	
}
