package it.polimi.ingsw.ps23.client.socket;

public interface Expression {

	public boolean interpret(String message);

	public String selectBlock(String message);

	public String removeBlock(String message);
	
}
