package it.polimi.ingsw.ps23.client.socket;

@FunctionalInterface
public interface Parser {
	
	public String parse(String message);
	
}
