package me.system32.coreminigames;

import me.system32.coreminigames.commands.Contador;
import me.system32.coreminigames.commands.Team;
import me.system32.coreminigames.events.Chat;
import me.system32.coreminigames.utils.ChatUtils;
import me.system32.coreminigames.utils.PvpManager;
import me.system32.coreminigames.utils.PlaceholderAPIExport;
import me.system32.coreminigames.utils.TeamManager;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Main extends JavaPlugin {
    ChatUtils chatUtils = new ChatUtils();
    public TeamManager teamManager = new TeamManager();
    public final List<String> red_team = new ArrayList<>();
    public final List<String> blue_team = new ArrayList<>();
    public final List<String> green_team = new ArrayList<>();
    public final Map<String, Integer> red_team_points = new HashMap<>();
    public final Map<String, Integer> blue_team_points = new HashMap<>();
    public final Map<String, Integer> green_team_points = new HashMap<>();
    public boolean ended = false;
    public long time = 0;
    public BukkitTask task;

    @Override
    public void onEnable() {
        registerConfig();
        loadCommands();
        loadEvents();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI")!=null){
            new PlaceholderAPIExport(this).register();
        }
        chatUtils.consoleMessage("Iniciando Sistema...");
    }

    @Override
    public void onDisable() {
        chatUtils.consoleMessage("Apagando Sistema...");
    }

    private void loadCommands(){
        this.getCommand("team").setExecutor(new Team(this));
        this.getCommand("contador").setExecutor(new Contador(this));
    }
    public void loadEvents() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PvpManager(this), this);
        pm.registerEvents(new Chat(this), this);
    }
    public void registerConfig() {
        File conf = new File(this.getDataFolder(), "config.yml");
        if (!conf.exists()) {
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }
    public void startTimer(){
        task = Bukkit.getScheduler().runTaskTimer(this, () -> {
            addTimer();
        },0L, 20L);
    }
    public void addTimer(){
        if(time==0){
            time=0;
            ended=true;
            for(Player player : Bukkit.getServer().getOnlinePlayers()){
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 10, 1);
                chatUtils.playerMessage(player, "Se acabó el tiempo!!");
                spawnFirework(player, 3, 3);
                spawnFirework(player, -2, 2);
                spawnFirework(player, 4, -4);
                player.getInventory().clear();
                //teleport
                task.cancel();
            }
        }else{
            time = time-1000;
            ended = false;
        }
    }
    private void spawnFirework(Player player, int x, int z){
        Firework fw = (Firework) player.getWorld().spawnEntity(player.getLocation().add(x, 1, z), EntityType.FIREWORK);
        FireworkMeta meta = (FireworkMeta) fw.getFireworkMeta();
        meta.setPower(1);
        List<Color> colores = new ArrayList<Color>();
        colores.add(Color.YELLOW); colores.add(Color.ORANGE);
        meta.addEffect(FireworkEffect.builder().flicker(true).trail(true).with(FireworkEffect.Type.BALL_LARGE).withColor(colores).build());
        fw.setFireworkMeta(meta);
    }
}
