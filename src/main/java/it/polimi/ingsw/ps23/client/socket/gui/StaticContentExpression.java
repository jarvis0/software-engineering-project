package it.polimi.ingsw.ps23.client.socket.gui;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.SocketSwingUI;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;

public class StaticContentExpression extends UIComponentsParser {

	private static final String REWARD_TOKENS_TAG_OPEN = "<reward_tokens>";
	private static final String REWARD_TOKENS_TAG_CLOSE = "</reward_tokens>";
	private static final String NOBILITY_TRACK_TAG_OPEN = "<nobility_track>";
	private static final String NOBILITY_TRACK_TAG_CLOSE = "</nobility_track>";

	private SocketSwingUI swingUI;
	
	private Expression expression;
	
	public StaticContentExpression(SocketSwingUI swingUI, Expression expression) {
		this.swingUI = swingUI;
		this.expression = expression;
	}

	private RewardTokensExpression getRewardTokensExpression() {
		Expression rewardTokensExpression = new TerminalExpression(REWARD_TOKENS_TAG_OPEN, REWARD_TOKENS_TAG_CLOSE);
		return new RewardTokensExpression(rewardTokensExpression);
	}
	
	private NobilityTrackExpression getNobilityTrackExpression() {
		Expression nobilityTrackExpression = new TerminalExpression(NOBILITY_TRACK_TAG_OPEN, NOBILITY_TRACK_TAG_CLOSE);
		return new NobilityTrackExpression(nobilityTrackExpression);
	}

	@Override
	public void parse(String message) {
		if(expression.interpret(message)) {
			String noTagMessage = expression.selectBlock(message);
			RewardTokensExpression areRewardTokens = getRewardTokensExpression();
			areRewardTokens.parse(noTagMessage);
			NobilityTrackExpression isNobilityTrack = getNobilityTrackExpression();
			isNobilityTrack.parse(noTagMessage);
			swingUI.loadStaticContent(areRewardTokens.getCitiesName(), areRewardTokens.getRewardTokensName(), areRewardTokens.getRewardTokensValue(), isNobilityTrack.getStepsName(), isNobilityTrack.getStepsValue());	
		}
	}
	
}
