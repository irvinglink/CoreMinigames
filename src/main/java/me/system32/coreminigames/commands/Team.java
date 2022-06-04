package me.system32.coreminigames.commands;

import me.system32.coreminigames.Main;
import me.system32.coreminigames.utils.ChatUtils;
import me.system32.coreminigames.utils.TeamManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Team implements CommandExecutor {
    private final Main main;

    public Team(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;
        ChatUtils chatUtils = new ChatUtils();
        String[] teamColors = {"red", "green", "blue", "spectator"};
        TeamManager teamManager = new TeamManager();
        FileConfiguration config = main.getConfig();
        if(args[0].equalsIgnoreCase("join")){
            if(args[1] == null){
                chatUtils.playerMessage(player, "Escribe el color del equipo");
                return false;
            }
            if(!Arrays.stream(teamColors).toList().contains(args[1])){
                chatUtils.playerMessage(player, "Color de equipo incorrecto, disponibles: red, blue, green y spectator");
                return false;
            }
            teamManager.addPlayerToTeam(player, args[1], main.red_team, main.blue_team, main.green_team);
        }else if(args[0].equalsIgnoreCase("leave")){
            if(args[1] == null){
                chatUtils.playerMessage(player, "Escribe el color del equipo");
                return false;
            }
            if(!Arrays.stream(teamColors).toList().contains(args[1])){
                chatUtils.playerMessage(player, "Color de equipo incorrecto, disponibles: red, blue, green y spectator");
                return false;
            }
            teamManager.removePlayerFromTeam(player, args[1], main.red_team, main.blue_team, main.green_team);
        }else if(args[0].equalsIgnoreCase("setTp")){
            if(args[1] == null){
                chatUtils.playerMessage(player, "Escribe el color del equipo");
                return false;
            }
            if(!Arrays.stream(teamColors).toList().contains(args[1])){
                chatUtils.playerMessage(player, "Color de equipo incorrecto, disponibles: red, blue, green y spectator");
                return false;
            }
            Location l = player.getLocation();
            double x = l.getX();
            double y = l.getY();
            double z = l.getZ();
            float yaw = l.getYaw();
            float pitch = l.getPitch();
            config.set("tp."+args[1]+".x", x);
            config.set("tp."+args[1]+".y", y);
            config.set("tp."+args[1]+".z", z);
            config.set("tp."+args[1]+".yaw", yaw);
            config.set("tp."+args[1]+".pitch", pitch);
            main.saveConfig();
            chatUtils.playerMessage(player, "Se ha establecido correctamente el tp de minijuego para el equipo " + args[1].replaceAll("red", "&cRojo").replaceAll("blue", "&9Azul").replaceAll("green", "&aVerde").replaceAll("spectator", "&4Espectador"));
        }else if(args[0].equalsIgnoreCase("setFinalTp")){
            Location l = player.getLocation();
            double x = l.getX();
            double y = l.getY();
            double z = l.getZ();
            float yaw = l.getYaw();
            float pitch = l.getPitch();
            config.set("tp.final.x", x);
            config.set("tp.final.y", y);
            config.set("tp.final.z", z);
            config.set("tp.final.yaw", yaw);
            config.set("tp.final.pitch", pitch);
            main.saveConfig();
            chatUtils.playerMessage(player, "Se ha establecido correctamente el tp final de minijuego");
        }else{
            chatUtils.playerMessage(player, "Faltan argumentos para este comando");
        }
        return false;
    }
}
