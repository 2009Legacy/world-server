package com.rs.game.player.content.commands.mod;

import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.content.commands.Commands;
import com.rs.lib.game.Rights;
import com.rs.lib.util.Utils;
import com.rs.net.LobbyCommunicator;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.annotations.ServerStartupEvent;
import com.rs.utils.Click;

@PluginEventHandler
public class PlayerModifiers {
	
	@ServerStartupEvent
	public static void loadCommands() {
		Commands.add(Rights.MOD, "kick [player name]", "Kicks a player from the game. Will not kick through combat.", (p, args) -> {
			Player target = World.getPlayer(Utils.concat(args));
			if (target == null) {
				p.sendMessage(Utils.formatPlayerNameForDisplay(Utils.concat(args)) + " is not logged in.");
				return;
			}
			p.sendMessage("Successfully kicked " + Utils.concat(args) + ".");
			target.getSession().getChannel().close();
		});
		
		Commands.add(Rights.MOD, "ban [player name]", "Bans a player for 2 days.", (p, args) -> {
			Player target = World.forceGetPlayer(Utils.concat(args));
			if (target != null) {
				target.getAccount().banDays(2);
				p.sendMessage("You have banned " + Utils.formatPlayerNameForDisplay(Utils.concat(args)) + " for 2 days.");
				LobbyCommunicator.updateAccount(target);
				if (target.hasStarted())
					target.getSession().getChannel().close();
			} else {
				p.sendMessage("Unable to find player.");
			}
		});
		
		Commands.add(Rights.MOD, "teleto", "Teleports the user to another player as long as they aren't in a controller or locked.", (p, args) -> {
			if (p.isLocked() || p.getControllerManager().getController() != null) {
				p.sendMessage("You cannot tele anywhere from here.");
				return;
			}
			Player target = World.getPlayer(Utils.concat(args));
			if (target == null)
				p.sendMessage("Couldn't find player.");
			else {
				if (target.isLocked() || target.getControllerManager().getController() != null) {
					p.sendMessage("You cannot teleport this player.");
					return;
				}
				p.setNextWorldTile(target);
			}
		});
		
		Commands.add(Rights.MOD, "teletome", "Teleports another player to the user as long as they aren't in a controller or locked.", (p, args) -> {
			Player target = World.getPlayer(Utils.concat(args));
			if (target == null)
				p.sendMessage("Couldn't find player.");
			else {
				if (target.isLocked() || target.getControllerManager().getController() != null) {
					p.sendMessage("You cannot teleport this player.");
					return;
				}
				if (target.hasRights(Rights.DEVELOPER)) {
					p.sendMessage("Unable to teleport a developer to you.");
					return;
				}
				target.setNextWorldTile(p);
			}
		});
		
		Commands.add(Rights.MOD, "mute [player name]", "Mutes a player for 2 days.", (p, args) -> {
			Player target = World.forceGetPlayer(Utils.concat(args));
			if (target != null) {
				target.getAccount().muteDays(2);
				p.sendMessage("You have muted " + Utils.formatPlayerNameForDisplay(Utils.concat(args)) + " for 2 days.");
				LobbyCommunicator.updateAccount(target);
			} else {
				p.sendMessage("Unable to find player.");
			}
		});
		
		Commands.add(Rights.MOD, "clicks [player name]", "Displays the last 50 clicks the player has done.", (p, args) -> {
			Player target = World.getPlayer(Utils.concat(args));
			if (target == null)
				p.sendMessage("Couldn't find player " + Utils.concat(args) + ".");
			else {
				if (target.clickQueue == null)
					return;
				p.getPackets().sendRunScriptReverse(1207, new Object[] { target.clickQueue.size() });
				p.getInterfaceManager().sendInterface(275);
				p.getPackets().setIFText(275, 1, "Past 50 clicks for:  " + Utils.concat(args));
				int numa = 10;
				for (Click click : target.clickQueue) {
					if (numa > 288)
						break;
					p.getPackets().setIFText(275, numa, ": " + click.toString());
					numa++;
				}
			}
		});
	}

}
