package it.polimi.ingsw.ps23.model.state;

public abstract class ActionState implements State, Cloneable {

	private String name;
	
	public ActionState(String name) {
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
