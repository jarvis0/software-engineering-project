package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;
/**
 * Provide the method to add a bonus into a {@link RewardToken}, {@link NobilityTrackStep} or {@link BusinessPermitTile}.
 * @author Giuseppe Mascellaro
 *
 */
@FunctionalInterface
public interface BonusSlot extends Serializable {
	/**
	 * Adds the references of the selected bonus in the {@link RewardToken}, {@link NobilityTrackStep} or {@link BusinessPermitTile}.
	 * @param bonus - the selected bonus
	 */
	public void addBonus(Bonus bonus);
	
}
