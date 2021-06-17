package com.rs.game.player.quests.handlers.dwarfcannon;

import com.rs.game.player.Player;
import com.rs.game.player.content.dialogue.Conversation;
import com.rs.game.player.content.dialogue.HeadE;
import com.rs.game.player.quests.Quest;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.NPCClickEvent;
import com.rs.plugin.handlers.NPCClickHandler;

@PluginEventHandler
public class GuardD extends Conversation {

	private static final int GUARD = 206;
	
	public static NPCClickHandler talkToLawgof = new NPCClickHandler(GUARD) {
		@Override
		public void handle(NPCClickEvent e) {
			e.getPlayer().startConversation(new GuardD(e.getPlayer()));
		}
	};

	public GuardD(Player player) {
		super(player);
		int currentStage = player.getQuestManager().getStage(Quest.DWARF_CANNON);
		switch(currentStage) {
			case 0:
				addPlayer(HeadE.NO_EXPRESSION, "Hello.");
				addNPC(GUARD, HeadE.ANGRY, "Don't distract me while I'm on duty! This mine has to be protected!");
				addPlayer(HeadE.CONFUSED, "What's going to attack a mine?");
				addNPC(GUARD, HeadE.ANGRY, "Goblins! They wander everywhere, attacking anyone they think is small enough to be an easy victim. We need more cannons to fight them off properly.");
				addPlayer(HeadE.NO_EXPRESSION, "Cannons? Those sound expensive.");
				addNPC(GUARD, HeadE.CALM, "A new cannon can cost 750,000 coins, and the ammo isn't easy to get, but they can do an enormous amount of damage with each shot. When you've got an important mine like this one to protect, it's worth the expense.");
				addPlayer(HeadE.HAPPY_TALKING, "Thanks for the information.");
				addNPC(GUARD, HeadE.CALM, "You're welcome. Now please let me get on with my guard duties.");
				addPlayer(HeadE.NO_EXPRESSION, "Alright, I'll leave you alone now.");
				break;
			default:
				addPlayer(HeadE.NERVOUS, "I should probably leave him to his duties.");
		}
		create();
	}

}