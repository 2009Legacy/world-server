package com.rs.game.player.quests.handlers.dragonslayer;

import com.rs.game.player.Player;
import com.rs.game.player.content.dialogue.Conversation;
import com.rs.game.player.content.dialogue.Dialogue;
import com.rs.game.player.content.dialogue.HeadE;
import com.rs.game.player.content.dialogue.Options;
import com.rs.game.player.controllers.DragonSlayer_BoatScene;
import com.rs.game.player.quests.Quest;
import com.rs.lib.game.Item;
import com.rs.lib.util.GenericAttribMap;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.NPCClickEvent;
import com.rs.plugin.handlers.NPCClickHandler;

import static com.rs.game.player.quests.handlers.dragonslayer.DragonSlayer.*;

@PluginEventHandler
public class CaptainNedDragonSlayerD extends Conversation {
    /**
     * Only called in PREPARE_FOR_CRANDOR stage.
     */
    public CaptainNedDragonSlayerD(Player p) {
        super(p);
        switch(p.getQuestManager().getStage(Quest.DRAGON_SLAYER)) {
            case NOT_STARTED, TALK_TO_OZIACH, TALK_TO_GUILDMASTER -> {
                addNPC(NED, HeadE.CALM_TALK, "I just came here for a little stroll...");
                addPlayer(HeadE.HAPPY_TALKING, "Oh, well carry on then...");
            }
            case PREPARE_FOR_CRANDOR -> {
                addNPC(NED, HeadE.CALM_TALK, "Ah, it's good to be on board a ship again! No matter how long I live on land, a ship will always seem better. " +
                        "Are you ready to depart?");
                addOptions("Choose an option:", new Options() {
                    @Override
                    public void create() {
                        option("Yes, let's go!", new Dialogue()
                                .addPlayer(HeadE.HAPPY_TALKING, "Yes, let's go!")
                                .addSimple("Ship begins to set sail")
                                .addNext(()->{p.getControllerManager().startController(new DragonSlayer_BoatScene());}));
                        option("No, I'm not quite ready yet.", new Dialogue()
                                .addPlayer(HeadE.HAPPY_TALKING, "No, I'm not quite ready yet.")
                                .addNPC(NED, HeadE.CALM_TALK, "Well, you go do whatever you need to do. I'll wait here for you until you're ready."));
                    }
                });

            }

            case REPORT_TO_OZIACH -> {
                addNPC(NED, HeadE.CALM_TALK, "Now I see why all of the other captains said I'd be mad to go near Crandor.");
                addPlayer(HeadE.HAPPY_TALKING, "Yea, now that the dragons dead, want to go again?");
                addNPC(NED, HeadE.CALM_TALK, "Ay, anything for the sea!");
            }

            case QUEST_COMPLETE -> {
                if(p.getQuestManager().getAttribs(Quest.DRAGON_SLAYER).getB(IS_BOAT_FIXED_ATTR)) {
                    addNPC(NED, HeadE.CALM_TALK, "This ship's in a sorry state. You'd better fix up the hole in the hull before we can go anywhere.");
                } else {
                    addNPC(NED, HeadE.CALM_TALK, "If you want to head to Crandor you better tell Klarense.");
                }
            }
        }
    }


    public static NPCClickHandler handleJenkinsDialogue = new NPCClickHandler(CAPTAIN_NED) {
        @Override
        public void handle(NPCClickEvent e) {
            e.getPlayer().startConversation(new CaptainNedDragonSlayerD(e.getPlayer()).getStart());
        }
    };
}