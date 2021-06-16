package com.rs.game.npc.combat.impl;

import com.rs.game.Entity;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.combat.NPCCombatDefinitions.AttackStyle;
import com.rs.game.player.Player;
import com.rs.game.player.actions.PlayerCombat;
import com.rs.lib.game.Animation;
import com.rs.lib.util.Utils;
import com.rs.utils.Ticks;

public class KingBlackDragonCombat extends CombatScript {

	@Override
	public Object[] getKeys() {
		return new Object[] { 50 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		int attackStyle = Utils.getRandomInclusive(5);
		int size = npc.getSize();

		if (attackStyle == 0) {
			int distanceX = target.getX() - npc.getX();
			int distanceY = target.getY() - npc.getY();
			if (distanceX > size || distanceX < -1 || distanceY > size || distanceY < -1)
				attackStyle = Utils.getRandomInclusive(4) + 1;
			else {
				delayHit(npc, 0, target, getMeleeHit(npc, getMaxHit(npc, defs.getMaxHit(), AttackStyle.MELEE, target)));
				npc.setNextAnimation(new Animation(defs.getAttackEmote()));
				return npc.getAttackSpeed();
			}
		} else if (attackStyle == 1 || attackStyle == 2) {
			int damage = Utils.getRandomInclusive(650);
			final Player player = target instanceof Player ? (Player) target : null;
			int protection = PlayerCombat.getAntifireLevel(player, true);
			if (protection == 1)
				damage = Utils.getRandomInclusive(150);
			if (protection == 2)
				damage = 0;
			delayHit(npc, 2, target, getRegularHit(npc, damage));
			World.sendProjectile(npc, target, 393, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(81));

		} else if (attackStyle == 3) {
			int damage = Utils.getRandomInclusive(650);
			final Player player = target instanceof Player ? (Player) target : null;
			int protection = PlayerCombat.getAntifireLevel(player, true);
			if (protection == 1)
				damage = getMaxHit(npc, 164, AttackStyle.MAGE, target);
			if (protection == 2)
				damage = getMaxHit(npc, 100, AttackStyle.MAGE, target);
			if (Utils.getRandomInclusive(2) == 0)
				target.getPoison().makePoisoned(80);
			delayHit(npc, 2, target, getRegularHit(npc, damage));
			World.sendProjectile(npc, target, 394, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(81));
		} else if (attackStyle == 4) {
			int damage = Utils.getRandomInclusive(650);
			final Player player = target instanceof Player ? (Player) target : null;
			int protection = PlayerCombat.getAntifireLevel(player, true);
			if (protection == 1)
				damage = getMaxHit(npc, 164, AttackStyle.MAGE, target);
			if (protection == 2)
				damage = getMaxHit(npc, 100, AttackStyle.MAGE, target);
			if (Utils.getRandomInclusive(2) == 0)
				target.freeze(Ticks.fromSeconds(15));
			delayHit(npc, 2, target, getRegularHit(npc, damage));
			World.sendProjectile(npc, target, 395, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(81));
		} else {
			int damage = Utils.getRandomInclusive(650);
			final Player player = target instanceof Player ? (Player) target : null;
			int protection = PlayerCombat.getAntifireLevel(player, true);
			if (protection == 1)
				damage = getMaxHit(npc, 164, AttackStyle.MAGE, target);
			if (protection == 2)
				damage = getMaxHit(npc, 100, AttackStyle.MAGE, target);
			delayHit(npc, 2, target, getRegularHit(npc, damage));
			World.sendProjectile(npc, target, 396, 34, 16, 30, 35, 16, 0);
			npc.setNextAnimation(new Animation(81));
		}
		return npc.getAttackSpeed();
	}
}
