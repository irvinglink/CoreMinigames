package me.system32.coreminigames.commands;

import me.system32.coreminigames.Main;
import me.system32.coreminigames.utils.ChatUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Contador implements CommandExecutor {
    private final Main main;

    public Contador(Main main) {
        this.main = main;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player player)) return false;
        ChatUtils chatUtils = new ChatUtils();
        FileConfiguration config = main.getConfig();
        if(args[0].equalsIgnoreCase("start")){
            if(!main.ended){
                chatUtils.playerMessage(player, "Ya hay una cuenta regresiva en juego");
                return false;
            }
            if(args[1]==null){
                chatUtils.playerMessage(player, "Ingresa la cantidad de tiempo a usar (El tiempo se usa en minutos)");
                return false;
            }
            if(Integer.parseInt(args[1])<0){
                chatUtils.playerMessage(player, "Ingresa la cantidad de tiempo a usar (El tiempo se usa en minutos)");
                return false;
            }
            if(args[2]==null){
                chatUtils.playerMessage(player, "Escribe que equipo va a participar: red, blue, green");
                return false;
            }
            if(args[2].equalsIgnoreCase("red")){
                for(String p : main.red_team){
                    Player player1 = Bukkit.getServer().getPlayer(p);
                    if(player1 !=null){
                        player1.setGameMode(GameMode.CREATIVE);
                        Location location = new Location(player.getWorld(), config.getDouble("tp.red.x"), config.getDouble("tp.red.y"), config.getDouble("tp.red.z"), config.getInt("tp.red.yaw"), config.getInt("tp.red.pitch"));
                        setArmourColor(player1, Color.RED);
                        player1.teleport(location);
                    }
                }
            }else if(args[2].equalsIgnoreCase("blue")) {
                for (String p : main.blue_team) {
                    Player player1 = Bukkit.getServer().getPlayer(p);
                    if (player1 != null) {
                        player1.setGameMode(GameMode.CREATIVE);
                        Location location = new Location(player.getWorld(), config.getDouble("tp.blue.x"), config.getDouble("tp.blue.y"), config.getDouble("tp.blue.z"), config.getInt("tp.blue.yaw"), config.getInt("tp.blue.pitch"));
                        setArmourColor(player1, Color.BLUE);
                        player1.teleport(location);
                    }
                }
            } else if(args[2].equalsIgnoreCase("green")) {
                for (String p : main.green_team) {
                    Player player1 = Bukkit.getServer().getPlayer(p);
                    if (player1 != null) {
                        player1.setGameMode(GameMode.ADVENTURE);
                        Location location = new Location(player.getWorld(), config.getDouble("tp.green.x"), config.getDouble("tp.green.y"), config.getDouble("tp.green.z"), config.getInt("tp.green.yaw"), config.getInt("tp.green.pitch"));
                        setArmourColor(player1, Color.GREEN);
                        player1.teleport(location);
                    }
                }
            }else{
                chatUtils.playerMessage(player, "Escribe que equipo va a participar: red, blue, green");
                return false;
            }

            main.time = Integer.parseInt(args[1])*((long)60*(long)1000);
            main.startTimer();
            chatUtils.playerMessage(player, "Contador iniciado");
            return true;
        }else if(args[0].equalsIgnoreCase("stop")){
            main.task.cancel();
            main.time = 0;
            chatUtils.playerMessage(player, "Contador apagado");
            return true;
        }else{
            chatUtils.playerMessage(player, "&b&lComandos de Contador:");
            chatUtils.playerMessage(player, "/contador start");
            chatUtils.playerMessage(player, "/contador stop");
        }
        return false;
    }
    public void setArmourColor(Player player, Color color){
        // --------------------  HELMET  --------------------
        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET, 1);
        LeatherArmorMeta helmet_meta = (LeatherArmorMeta) helmet.getItemMeta();
        helmet_meta.setColor(color);
        helmet_meta.setDisplayName(player.getName());
        helmet_meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS);
        helmet_meta.setUnbreakable(true);
        helmet.setItemMeta(helmet_meta);
        player.getInventory().setHelmet(helmet);
        // --------------------  CHESTPLATE  --------------------
        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        LeatherArmorMeta chestplate_meta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplate_meta.setColor(color);
        chestplate_meta.setDisplayName(player.getName());
        chestplate_meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS);
        chestplate_meta.setUnbreakable(true);
        chestplate.setItemMeta(chestplate_meta);
        player.getInventory().setChestplate(chestplate);
        // --------------------  LEGGINGS  --------------------
        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
        LeatherArmorMeta leggings_meta = (LeatherArmorMeta) leggings.getItemMeta();
        leggings_meta.setColor(color);
        leggings_meta.setDisplayName(player.getName());
        leggings_meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS);
        leggings_meta.setUnbreakable(true);
        leggings.setItemMeta(leggings_meta);
        player.getInventory().setLeggings(leggings);
        // --------------------  BOOTS  --------------------
        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
        LeatherArmorMeta boots_meta = (LeatherArmorMeta) boots.getItemMeta();
        boots_meta.setColor(color);
        boots_meta.setUnbreakable(true);
        boots_meta.setDisplayName(player.getName());
        boots_meta.addItemFlags(ItemFlag.HIDE_DYE, ItemFlag.HIDE_ENCHANTS);
        boots.setItemMeta(boots_meta);
        player.getInventory().setBoots(boots);
    }
}
