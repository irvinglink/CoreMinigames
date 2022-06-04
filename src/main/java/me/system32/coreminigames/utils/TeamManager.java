package me.system32.coreminigames.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamManager {

    ChatUtils chatUtils = new ChatUtils();

    public String getPlayerTeam(Player player, List<String> red_team, List<String> blue_team, List<String> green_team){
        String player_name = player.getName();
        if(red_team.contains(player_name)){
            return "red";
        }else if(blue_team.contains(player_name)){
            return "blue";
        }else if(green_team.contains(player_name)){
            return "green";
        }else{
            return "spectator";
        }
    }
    public Boolean checkIfSameTeam(Player damager, Player reciever, List<String> red_team, List<String> blue_team, List<String> green_team){
        return getPlayerTeam(damager, red_team, blue_team, green_team).equals(getPlayerTeam(reciever, red_team, blue_team, green_team));
    }
    public void addPlayerToTeam(Player player, String team, List<String> red_team, List<String> blue_team, List<String> green_team){
        String player_name = player.getName();
        if(team.equalsIgnoreCase("red")){
            if(red_team.contains(player_name)){
                chatUtils.playerMessage(player, "Ya estás en el equipo &cRojo");
            }else{
                if(!blue_team.contains(player_name) && !green_team.contains(player_name)){
                    chatUtils.playerMessage(player, "Acabas de unirte al equipo &cRojo");
                    red_team.add(player_name);
                }else{
                    chatUtils.playerMessage(player, "Ya estás en otro equipo");
                }

            }
        }else if(team.equalsIgnoreCase("blue")){
            if(blue_team.contains(player_name)){
                chatUtils.playerMessage(player, "Ya estás en el equipo &9Azul");
            }else{
                if(!red_team.contains(player_name) && !green_team.contains(player_name)){
                    chatUtils.playerMessage(player, "Acabas de unirte al equipo &9Azul");
                    blue_team.add(player_name);
                }else{
                    chatUtils.playerMessage(player, "Ya estás en otro equipo");
                }
            }
        }else if(team.equalsIgnoreCase("green")){
            if(green_team.contains(player_name)){
                chatUtils.playerMessage(player, "Ya estás en el equipo &aVerde");
            }else{
                if(!red_team.contains(player_name) && !blue_team.contains(player_name)){
                    chatUtils.playerMessage(player, "Acabas de unirte al equipo &aVerde");
                    green_team.add(player_name);
                }else{
                    chatUtils.playerMessage(player, "Ya estás en otro equipo");
                }
            }
        }else{
            chatUtils.playerMessage(player, "Equipo desconocido");
        }
    }
    public void removePlayerFromTeam(Player player, String team, List<String> red_team, List<String> blue_team, List<String> green_team){
        String player_name = player.getName();
        if(team.equalsIgnoreCase("red")){
            if(red_team.contains(player_name)){
                chatUtils.playerMessage(player, "Acabas de salir del equipo &cRojo");
                red_team.remove(player_name);
            }else{
                chatUtils.playerMessage(player, "No estás en el equipo &cRojo");
            }
        }else if(team.equalsIgnoreCase("blue")){
            if(blue_team.contains(player_name)){
                chatUtils.playerMessage(player, "Acabas de salir del equipo &9Azul");
                blue_team.remove(player_name);
            }else{
                chatUtils.playerMessage(player, "No estás en el equipo &9Azul");
            }
        }else if(team.equalsIgnoreCase("green")){
            if(green_team.contains(player_name)){
                chatUtils.playerMessage(player, "Acabas de salir del equipo &aVerde");
                green_team.remove(player_name);
            }else{
                chatUtils.playerMessage(player, "No estás en el equipo &Verde");
            }
        }else{
            chatUtils.playerMessage(player, "Equipo desconocido");
        }
    }
}
