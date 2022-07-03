package com.ethnicthv.testingplugin.manager;

import com.ethnicthv.testingplugin.sickness.Disease;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;

public class Manager implements Listener {
    private String path;

    private final Ticker ticker;

    Map<UUID, List<Disease>> data = new HashMap<>();

    public Manager(JavaPlugin plugin){
        //Create data folder
        path = plugin.getDataFolder().getAbsolutePath() + "/playerdata";
        File file = new File(path);
        if(!file.exists()) {
            if (!file.mkdirs()) {
                plugin.getLogger().log(Level.ALL, "Cannot create '/playerdata' folder.");
            }
        }
        //

        //Setup Events
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        //

        //Setup ticker
        ticker = new Ticker(plugin);
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, ticker, 20L, 20L);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(!data.containsKey(uuid)){
            try {
                if(!load(uuid)){
                    data.put(uuid, new ArrayList<>());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        if(data.containsKey(uuid)){
            try {
                if(!save(uuid)){
                    data.remove(uuid);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Load data of a player
     * @param uuid
     * @return return if the loading is success
     * @throws IOException
     */
    boolean load(UUID uuid) throws IOException {
        File file = new File(path + "/" + uuid.toString());
        if(file.exists()){
            FileInputStream input = new FileInputStream(file);
            ObjectInputStream objectinput = new ObjectInputStream(input);
            try {
                List<Disease> temp = (List<Disease>) objectinput.readObject();
                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            objectinput.close();
            input.close();
        }
        file = null;
        return false;
    }

    /**
     * Save data of a player
     * @param uuid
     * @return return if the saving is success
     * @throws IOException
     */
    boolean save(UUID uuid) throws IOException {
        File file = new File(path + "/" + uuid.toString());
        if(file.exists()){
            FileOutputStream output = new FileOutputStream(file);
            ObjectOutput objectoutput = new ObjectOutputStream(output);
            objectoutput.writeObject(data.get(uuid));
            objectoutput.close();
            output.close();
            return true;
        }
        file = null;
        return false;
    }

    class Ticker implements Runnable {
        JavaPlugin plugin;

        Ticker(JavaPlugin plugin){
            this.plugin = plugin;
        }

        @Override
        public void run() {
            data.forEach(new BiConsumer<UUID, List<Disease>>() {
                @Override
                public void accept(UUID uuid, List<Disease> diseases) {
                    Player player = plugin.getServer().getPlayer(uuid);
                    diseases.forEach(new Consumer<Disease>() {
                        @Override
                        public void accept(Disease disease) {
                            Bukkit.getScheduler().runTask(plugin, new Runnable() {
                                @Override
                                public void run() {
                                    disease.onTick(player);
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
