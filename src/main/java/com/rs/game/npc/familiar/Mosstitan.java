package com.rs.game.npc.familiar;

import com.rs.game.player.Player;
import com.rs.game.player.content.skills.summoning.Summoning.Pouches;
import com.rs.lib.Constants;
import com.rs.lib.game.Animation;
import com.rs.lib.game.SpotAnim;
import com.rs.lib.game.WorldTile;

public class Mosstitan extends Familiar {

	public Mosstitan(Player owner, Pouches pouch, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(owner, pouch, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
	}

	@Override
	public String getSpecialName() {
		return "Titan's Constitution ";
	}

	@Override
	public String getSpecialDescription() {
		return "Defence by 12.5%, and it can also increase a player's Life Points 80 points higher than their max Life Points.";
	}

	@Override
	public int getBOBSize() {
		return 0;
	}

	@Override
	public int getSpecialAmount() {
		return 20;
	}

	@Override
	public SpecialAttack getSpecialAttack() {
		return SpecialAttack.CLICK;
	}

	@Override
	public boolean submitSpecial(Object object) {
		int newLevel = getOwner().getSkills().getLevel(Constants.DEFENSE) + (getOwner().getSkills().getLevelForXp(Constants.DEFENSE) / (int) 12.5);
		if (newLevel > getOwner().getSkills().getLevelForXp(Constants.DEFENSE) + (int) 12.5)
			newLevel = getOwner().getSkills().getLevelForXp(Constants.DEFENSE) + (int) 12.5;
		getOwner().setNextSpotAnim(new SpotAnim(2011));
		getOwner().setNextAnimation(new Animation(7660));
		getOwner().getSkills().set(Constants.DEFENSE, newLevel);
		return true;
	}
}