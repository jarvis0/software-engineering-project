package it.polimi.ingsw.ps23.server.model.bonus;

import java.io.Serializable;
/**
 * provide the method to add a bonus into a {@link RewardToken}, {@link NobilityTrackStep} or {@link BusinessPermitTile}
 * @author Giuseppe Mascellaro
 *
 */
@FunctionalInterface
public interface BonusSlot extends Serializable {

	public void addBonus(Bonus bonus);
	
}
