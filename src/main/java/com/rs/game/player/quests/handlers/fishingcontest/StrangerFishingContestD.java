package com.rs.game.player.quests.handlers.fishingcontest;

import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.dialogue.Conversation;
import com.rs.game.player.content.dialogue.Dialogue;
import com.rs.game.player.content.dialogue.HeadE;
import com.rs.game.player.content.dialogue.Options;
import com.rs.game.player.content.skills.fishing.Fishing;
import com.rs.game.player.content.skills.fishing.FishingSpot;
import com.rs.game.player.quests.Quest;
import com.rs.lib.util.GenericAttribMap;
import com.rs.plugin.annotations.PluginEventHandler;
import com.rs.plugin.events.NPCClickEvent;
import com.rs.plugin.handlers.NPCClickHandler;

import static com.rs.game.player.quests.handlers.fishingcontest.FishingContest.*;

@PluginEventHandler
public class StrangerFishingContestD extends Conversation {
    private static final int NPC = 3677;


    public StrangerFishingContestD(Player p) {
        super(p);
        switch(p.getQuestManager().getStage(Quest.FISHING_CONTEST)) {
            case NOT_STARTED -> {
                addPlayer(HeadE.HAPPY_TALKING, "Hi, what are you doing here?");
                addNPC(NPC, HeadE.CALM_TALK, "I am waiting for the fishing contest to start.");
                addPlayer(HeadE.HAPPY_TALKING, "Oh.");
            }
            case ENTER_COMPETITION, DO_ROUNDS -> {
                addNPC(NPC, HeadE.CALM_TALK, "...");
                addOptions("Choose an option:", new Options() {
                    @Override
                    public void create() {
                        option("...?", new Dialogue()
                                .addPlayer(HeadE.HAPPY_TALKING, "...?")
                                .addNPC(NPC, HeadE.CALM_TALK, "...")
                                .addPlayer(HeadE.HAPPY_TALKING, ".....?")
                                .addNPC(NPC, HeadE.CALM_TALK, ".....")

                        );
                        option("Who are you?", new Dialogue()
                                .addPlayer(HeadE.HAPPY_TALKING, "Who are you?")
                                .addNPC(NPC, HeadE.CALM_TALK, "My name is Vlad. I come from far avay, vere the sun iz not so bright.")
                                .addOptions("Choose an option:", new Options() {
                                    @Override
                                    public void create() {
                                        option("You're a vampyre aren't you?", new Dialogue()
                                                .addPlayer(HeadE.HAPPY_TALKING, "You're a vampyre aren't you?")
                                                .addNPC(NPC, HeadE.CALM_TALK, "Just because I can't stand ze smell ov garlic und I don't like bright " +
                                                        "sunlight doesn't necessarily mean I'm ein vampyre!")
                                        );
                                        option(" So... you like fishing?", new Dialogue()
                                                .addPlayer(HeadE.HAPPY_TALKING, " So... you like fishing?")
                                                .addNPC(NPC, HeadE.CALM_TALK, "My doctor told me to take up ein velaxing hobby. Vhen I am stressed I tend " +
                                                        "to get ein little... thirsty.")
                                        );
                                        option("Well, good luck with the fishing", new Dialogue()
                                                .addPlayer(HeadE.HAPPY_TALKING, "Well, good luck with the fishing")
                                                .addNPC(NPC, HeadE.CALM_TALK, "Luck haz notsing to do vith it. It is all in ze technique.")
                                        );

                                    }
                                })
                        );
                    }
                });
            }
            case GIVE_TROPHY, QUEST_COMPLETE ->  {
                addNPC(NPC, HeadE.CALM_TALK, "Congratulations, that competition made me thirsty...");
                addPlayer(HeadE.CALM_TALK, "...");
            }
        }
    }

    public static NPCClickHandler handleDialogue = new NPCClickHandler(NPC) {
        @Override
        public void handle(NPCClickEvent e) {
            e.getPlayer().startConversation(new StrangerFishingContestD(e.getPlayer()).getStart());
        }
    };

    public static NPCClickHandler handleStrangerFishingSpot = new NPCClickHandler(true, new Object[] { 234 }) {
        @Override
        public void handle(NPCClickEvent e) {
            Player p = e.getPlayer();
            NPC npc = e.getNPC();
            if(npc.getRegionId() == 10549) {
                if (p.getQuestManager().getStage(Quest.FISHING_CONTEST) >= GIVE_TROPHY) {
                    p.sendMessage("Nothing interesting happens...");
                    return;
                }
                e.getNPC().resetDirection();
                if(p.getQuestManager().getStage(Quest.FISHING_CONTEST) == DO_ROUNDS && p.getQuestManager().getAttribs(Quest.FISHING_CONTEST).getB(PIPE_HAS_GARLIC))
                    e.getPlayer().getActionManager().setAction(new Fishing(FishingSpot.GIANT_CARP, e.getNPC()));
                else
                    if(p.getQuestManager().getAttribs(Quest.FISHING_CONTEST).getB(PIPE_HAS_GARLIC)) {
                        p.startConversation(new Conversation(p) {
                            {
                                addNPC(225, HeadE.CALM_TALK, "Hey, you need to pay to enter the competition first! Only 5 coins for the entrance fee!");//Bonzo
                                create();
                            }
                        });
                    } else
                        p.startConversation(new Conversation(p) {
                            {
                                addNPC(NPC, HeadE.CALM_TALK, "I think you will find that is my spot.");
                                addPlayer(HeadE.HAPPY_TALKING, "Can't you go to another spot?");
                                addNPC(NPC, HeadE.CALM_TALK, "I like this place. I like to savour the aroma coming from these pipes.");
                                create();
                            }
                        });
            }

        }
    };
}