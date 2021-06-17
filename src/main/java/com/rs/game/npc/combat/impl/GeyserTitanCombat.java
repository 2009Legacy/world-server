package com.rs.game.npc.combat.impl;

import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions.AttackStyle;
import com.rs.game.npc.familiar.Familiar;
import com.rs.lib.game.Animation;
import com.rs.lib.game.SpotAnim;
import com.rs.lib.util.Utils;

public class GeyserTitanCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 7340, 7339 };
	}

	@Override
	public int attack(NPC npc, Entity target) {
		int distanceX = target.getX() - npc.getX();
		int distanceY = target.getY() - npc.getY();
		boolean distant = false;
		int size = npc.getSize();
		Familiar familiar = (Familiar) npc;
		boolean usingSpecial = familiar.hasSpecialOn();
		int damage = 0;
		if (distanceX > size || distanceX < -1 || distanceY > size || distanceY < -1)
			distant = true;
		if (usingSpecial) {// priority over regular attack
			npc.setNextAnimation(new Animation(7883));
			npc.setNextSpotAnim(new SpotAnim(1373));
			if (distant) {// range hit
				if (Utils.getRandomInclusive(2) == 0)
					delayHit(npc, 1, target, getRangeHit(npc, getMaxHit(npc, 300, AttackStyle.RANGE, target)));
				else
					delayHit(npc, 1, target, getMagicHit(npc, getMaxHit(npc, 300, AttackStyle.MAGE, target)));
			} else {// melee hit
				delayHit(npc, 1, target, getMeleeHit(npc, getMaxHit(npc, 300, AttackStyle.MELEE, target)));
			}
			World.sendProjectile(npc, target, 1376, 34, 16, 30, 35, 16, 0);
		} else {
			if (distant) {// range
				damage = getMaxHit(npc, 244, AttackStyle.RANGE, target);
				npc.setNextAnimation(new Animation(7883));
				npc.setNextSpotAnim(new SpotAnim(1375));
				World.sendProjectile(npc, target, 1374, 34, 16, 30, 35, 16, 0);
				delayHit(npc, 2, target, getRangeHit(npc, damage));
			} else {// melee
				damage = getMaxHit(npc, 244, AttackStyle.MELEE, target);
				npc.setNextAnimation(new Animation(7879));
				delayHit(npc, 1, target, getMeleeHit(npc, damage));
			}
		}
		return npc.getAttackSpeed();
	}
}