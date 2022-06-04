package me.system32.coreminigames.utils;

import me.system32.coreminigames.Main;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class PvpManager implements Listener {
    public final Map<String, Boolean> playersWithPvp = new HashMap<>();
    ChatUtils chatUtils = new ChatUtils();
    TeamManager teamManager = new TeamManager();
    private final Main main;

    public PvpManager(Main main) {
        this.main = main;
    }

    public void setPlayerPvp(String player, Boolean haspvp) {
        playersWithPvp.put(player, haspvp);
    }
    public Boolean getPlayerPvp(String player) {
        return playersWithPvp.get(player);
    }
    public void setPvp(Boolean mode){
        if(mode){
            for(Map.Entry<String, Boolean> en : playersWithPvp.entrySet()){
                Player p = Bukkit.getServer().getPlayerExact(en.getKey());
                assert p != null;
                if(!teamManager.getPlayerTeam(p, main.red_team, main.blue_team, main.green_team).equals("spectator")){
                    playersWithPvp.put(en.getKey(), true);
                }else{
                    playersWithPvp.put(en.getKey(), false);
                }
            }
        }else{
            playersWithPvp.replaceAll((k, v) -> false);
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(chatUtils.colorMessage("&7(&a+&7) &f"+player.getName()));
        playersWithPvp.put(player.getName(), false);
        player.setGameMode(GameMode.ADVENTURE);
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        event.setQuitMessage(chatUtils.colorMessage("&7(&c-&7) &f"+player.getName()));
        playersWithPvp.remove(player.getName());
    }
    @EventHandler
    public void sheepMinigame(EntityDamageByEntityEvent event){

        if (!(event.getDamager() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getDamager();

        if (!(arrow.getShooter() instanceof Player)) {
            return;
        }

        Player shooter = (Player) arrow.getShooter();

        if(!(event.getEntity() instanceof Sheep)){
            return;
        }
        Sheep sheep = (Sheep) event.getEntity();

        sheep.setInvulnerable(true);
        sheep.remove();
        shooter.playSound(shooter.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
        chatUtils.broadcastMessage("&a"+ shooter.getName() + " &fconsiguió un punto para el equipo "+ teamManager.getPlayerTeam(shooter, main.red_team, main.blue_team, main.green_team).replaceAll("red", "&cRojo").replaceAll("blue", "&9Azul").replaceAll("green", "&aVerde"));
    }
    @EventHandler
    public void pvpListener(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Arrow) {

            Arrow arrow = (Arrow) event.getDamager();

            if (!(arrow.getShooter() instanceof Player)) {
                return;
            }

            Player damager = (Player) arrow.getShooter();

            if(!(event.getEntity() instanceof Player)){
                return;
            }
            Player reciever = (Player) event.getEntity();

            if(teamManager.checkIfSameTeam(damager, reciever, main.red_team, main.blue_team, main.green_team)){
                chatUtils.playerMessage(damager, "No puedes hacerle daño a miembros de tu equipo");
                event.setCancelled(true);
            }else{
                if(teamManager.getPlayerTeam(reciever, main.red_team, main.blue_team, main.green_team).equals("spectator")){
                    event.setCancelled(true);
                    return;
                }
                if(!getPlayerPvp(damager.getName())){
                    event.setCancelled(true);
                }else{
                    if(!getPlayerPvp(reciever.getName())){
                        event.setCancelled(true);
                    }
                }
            }

        }



        //

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player damager = (Player) event.getDamager();

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player reciever = (Player) event.getEntity();

        if(teamManager.checkIfSameTeam(damager, reciever, main.red_team, main.blue_team, main.green_team)){
            chatUtils.playerMessage(damager, "No puedes hacerle daño a miembros de tu equipo");
            event.setCancelled(true);
        }else{
            if(teamManager.getPlayerTeam(reciever, main.red_team, main.blue_team, main.green_team).equals("spectator")){
                event.setCancelled(true);
                return;
            }
            if(!getPlayerPvp(damager.getName())){
                event.setCancelled(true);
            }else{
                if(!getPlayerPvp(reciever.getName())){
                    event.setCancelled(true);
                }
            }
        }

    }
}
