package it.polimi.ingsw.ps23.client.socket;

public class TerminalExpression implements Expression {

	private String tagOpen;
	private String tagClose;
	
	public TerminalExpression(String tagOpen, String tagClose) {
		this.tagOpen = tagOpen;
		this.tagClose = tagClose;
	}
	
	@Override
	public String selectBlock(String message) {
		int start = message.indexOf(tagOpen) + tagOpen.length();
		int end = message.indexOf(tagClose);
		return message.substring(start, end);
	}

	@Override
	public String removeBlock(String message) {
		return message.substring(message.indexOf(tagClose) + tagClose.length());
	}

	@Override
	public boolean interpret(String message) {
		return message.contains(tagOpen) && message.contains(tagClose);
	}

}
