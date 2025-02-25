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
package com.rs.game.npc.pestqueen;

import com.rs.game.npc.NPC;
import com.rs.game.npc.pestqueen.attack.Attack;
import com.rs.game.npc.pestqueen.attack.impl.MeleeAttack;
import com.rs.lib.game.WorldTile;

/**
 *
 * @author Tyler
 *
 */
public class PestQueen extends NPC {

	public Attack currentAttack;

	public PestQueen(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		super(id, tile);
		setHitpoints(20000);
		setCombatLevel(599);
		currentAttack = new MeleeAttack();
	}

	@Override
	public void processNPC() {

	}
}
