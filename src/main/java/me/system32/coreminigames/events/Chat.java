package me.system32.coreminigames.events;

import me.system32.coreminigames.Main;
import me.system32.coreminigames.utils.ChatUtils;
import me.system32.coreminigames.utils.TeamManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Chat implements Listener {
    ChatUtils chatUtils = new ChatUtils();
    private final Main main;

    public Chat(Main main) {
        this.main = main;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        TeamManager teamManager = new TeamManager();
        Player p = event.getPlayer();
        System.out.print("r " + main.red_team);
        System.out.print("b " + main.blue_team);
        System.out.print("g " + main.green_team);
        String name = chatUtils.colorMessage(teamManager.getPlayerTeam(p, main.red_team, main.blue_team, main.green_team).replaceAll("red", "&c").replaceAll("blue", "&9").replaceAll("green", "&a").replace("spectator", "&4"));
        event.setFormat(name+chatUtils.colorMessage("%s &f%s"));
    }
}
