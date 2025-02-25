// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.
//
//  Copyright (C) 2021 Trenton Kress
//  This file is part of project: Darkan
//
package com.rs.game.npc.godwars.zaros.attack;

import com.rs.game.Entity;
import com.rs.game.ForceMovement;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.pathing.Direction;
import com.rs.game.player.Player;
import com.rs.game.player.cutscenes.NexCutScene;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasks;
import com.rs.lib.game.Animation;
import com.rs.lib.game.SpotAnim;
import com.rs.lib.game.WorldTile;
import com.rs.lib.util.Utils;

public class NoEscape implements NexAttack {

	public static WorldTile[] NO_ESCAPE_TELEPORTS = {
			new WorldTile(2924, 5213, 0), //north
			new WorldTile(2934, 5202, 0), //east,
			new WorldTile(2924, 5192, 0), //south
			new WorldTile(2913, 5202, 0), }; //west

	@Override
	public int attack(Nex nex, Entity target) {
		nex.setNextForceTalk(new ForceTalk("There is..."));
		nex.playSound(3294, 2);
		nex.setCantInteract(true);
		nex.getCombat().removeTarget();
		final int idx = Utils.random(NO_ESCAPE_TELEPORTS.length);
		final WorldTile dir = NO_ESCAPE_TELEPORTS[idx];
		final WorldTile center = new WorldTile(2924, 5202, 0);
		WorldTasks.schedule(new WorldTask() {
			private int count;

			@Override
			public void run() {
				if (count == 0) {
					nex.setNextAnimation(new Animation(6321));
					nex.setNextSpotAnim(new SpotAnim(1216));
				} else if (count == 1) {
					nex.setNextWorldTile(dir);
					nex.setNextForceTalk(new ForceTalk("NO ESCAPE!"));
					nex.playSound(3292, 2);
					nex.setNextForceMovement(new ForceMovement(dir, 1, center, 3, idx == 3 ? 1 : idx == 2 ? 0 : idx == 1 ? 3 : 2));
					for (Entity entity : nex.calculatePossibleTargets(center, dir, idx == 0 || idx == 2))
						if (entity instanceof Player player) {
							player.getCutsceneManager().play(new NexCutScene(dir, idx));
							player.applyHit(new Hit(nex, Utils.getRandomInclusive(650), HitLook.TRUE_DAMAGE));
							player.setNextAnimation(new Animation(10070));
							player.setNextForceMovement(new ForceMovement(player.getTile(), 1, idx == 3 ? Direction.WEST : idx == 2 ? Direction.SOUTH : idx == 1 ? Direction.EAST : Direction.NORTH));
						}
				} else if (count == 3)
					nex.setNextWorldTile(center);
				else if (count == 4) {
					nex.setTarget(target);
					nex.setCantInteract(false);
					stop();
				}
				count++;
			}
		}, 0, 1);
		return nex.getAttackSpeed();
	}

}
