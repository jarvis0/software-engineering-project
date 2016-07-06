package it.polimi.ingsw.ps23.client.socket.gui.interpreter.components;

import it.polimi.ingsw.ps23.client.socket.Expression;
import it.polimi.ingsw.ps23.client.socket.TerminalExpression;
import it.polimi.ingsw.ps23.client.socket.gui.interpreter.GUIParser;
/**
 * Provides methods to parse and get available all the static info of the game like 
 * {@link RewardToken} and {@link NobilityTrack}.
 * @author Giuseppe Mascellaro
 *
 */
public class StaticContentExpression extends GUIParser {

	private static final String REWARD_TOKENS_TAG_OPEN = "<reward_tokens>";
	private static final String REWARD_TOKENS_TAG_CLOSE = "</reward_tokens>";
	private static final String NOBILITY_TRACK_TAG_OPEN = "<nobility_track>";
	private static final String NOBILITY_TRACK_TAG_CLOSE = "</nobility_track>";

	private SocketSwingUI swingUI;
	
	private Expression expression;
	/**
	 * Constructs the object initializing all the variables to the default values.
	 * @param swingUI - Interface to interact with UI
	 * @param expression - Tags used in socket protocol
	 */
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
			swingUI.loadStaticContents(areRewardTokens.getCitiesName(), areRewardTokens.getRewardTokensName(), areRewardTokens.getRewardTokensValue(), isNobilityTrack.getStepsName(), isNobilityTrack.getStepsValue());	
		}
	}
	
}
