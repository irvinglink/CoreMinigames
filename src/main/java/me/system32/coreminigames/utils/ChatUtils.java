package me.system32.coreminigames.utils;

import me.system32.coreminigames.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;

public class ChatUtils implements Listener {
    private final String prefix;

    public ChatUtils(){
        this.prefix = "&b&lSistema ►&f ";
    }
    public void broadcastMessage(String message){
        Bukkit.getServer().broadcastMessage(colorMessage(prefix+message));
    }
    public void consoleMessage(String message){
        Bukkit.getConsoleSender().sendMessage(colorMessage(prefix+message));
    }
    public void playerMessage(Player player, String message){
        player.sendMessage(colorMessage(prefix+message));
    }
    public String colorMessage(String message){
       return ChatColor.translateAlternateColorCodes('&', message);
    }

}
