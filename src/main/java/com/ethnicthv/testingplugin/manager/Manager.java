package com.ethnicthv.testingplugin.manager;

import com.ethnicthv.testingplugin.sickness.Disease;
import com.ethnicthv.testingplugin.sickness.symptom.Symptom;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.logging.Level;

public class Manager implements Listener {
    private final String path;

    private final Ticker ticker;

    Map<UUID, List<Disease>> diseasedata = new HashMap<>();
    Map<UUID, List<Symptom>> symptomdata = new HashMap<>();
    private final JavaPlugin plugin;

    public Manager(@NotNull JavaPlugin plugin){
        this.plugin = plugin;

        //Create diseasedata folder
        path = plugin.getDataFolder().getAbsolutePath() + "/playerdata";
        File file = new File(path);
        if(!file.exists()) {
            if (!file.mkdirs()) {
                plugin.getLogger().log(Level.ALL, "Cannot create '/playerdata' folder.");
            }
        }
        //

        //Setup Events
        this.plugin.getServer().getPluginManager().registerEvents(this,plugin);
        //

        //Setup ticker
        ticker = new Ticker(plugin);
        ticker.runTaskTimerAsynchronously(plugin,10,10);
    }

    /**
     * Add a Disease to a Player
     * @param uuid the uuid of infected player
     * @param disease the disease that infecting the player
     */
    public void addDisease(UUID uuid, Disease disease){
        if (diseasedata.get(uuid).contains(disease)){
            int index = diseasedata.get(uuid).indexOf(disease);
            Disease temp = diseasedata.get(uuid).get(index);
            //Do something here -[Disease State - WIP]-
        }else{
            diseasedata.get(uuid).add(disease);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        plugin.getLogger().log( Level.INFO,"Player Join: Loading Data");
        UUID uuid = event.getPlayer().getUniqueId();
        if(!diseasedata.containsKey(uuid)){
            try {
                if(!load(uuid)){
                    plugin.getLogger().log( Level.INFO,"Player Join: No Data Found");
                    diseasedata.put(uuid, new ArrayList<>());
                }else{
                    plugin.getLogger().log( Level.INFO,"Player Join: Data Loaded");
                }
            } catch (IOException e) {
                plugin.getLogger().log( Level.WARNING,"Player Join: Load Fail");
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        UUID uuid = event.getPlayer().getUniqueId();
        plugin.getLogger().log( Level.INFO,"Player Join: Saving Data");
        if(diseasedata.containsKey(uuid)){
            try {
                if(save(uuid)){
                    diseasedata.remove(uuid);
                }
            } catch (IOException e) {
                plugin.getLogger().log( Level.WARNING,"Player Join: Save Fail");
                e.printStackTrace();
            }
        }
    }

    public void saveWhenServerShutdown(){
        diseasedata.forEach((uuid, diseases) -> {
            try {
                save(uuid);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    /**
     * Load diseasedata of a player
     * @param uuid the uuid of joining player
     * @return return if the loading is success
     * @throws IOException throw when something wrong with input
     */
    boolean load(UUID uuid) throws IOException {
        File file = new File(path + "/" + uuid.toString());
        plugin.getLogger().log( Level.INFO,"loading path: " + file.getAbsolutePath());
        if(file.exists()){
            FileInputStream input = new FileInputStream(file);
            ObjectInputStream objectinput = new ObjectInputStream(input);
            try {
                List<Object> a = (List<Object>) objectinput.readObject();
                List<Disease> temp1 = (List<Disease>) a.get(0);
                if (diseasedata.containsKey(uuid)) {
                    diseasedata.replace(uuid, temp1);
                } else {
                    diseasedata.put(uuid, temp1);
                }
                List<Symptom> temp2 = (List<Symptom>) a.get(1);
                if (symptomdata.containsKey(uuid)) {
                    symptomdata.replace(uuid, temp2);
                } else {
                    symptomdata.put(uuid, temp2);
                }
                return true;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            objectinput.close();
            input.close();
        }
        return false;
    }

    /**
     * Save diseasedata of a player
     * @param uuid the uuid of leaving player
     * @return return if the saving is success
     * @throws IOException throw when something wrong with output
     */
    boolean save(UUID uuid) throws IOException {
        File file = new File(path + "/" + uuid.toString());
        plugin.getLogger().log( Level.INFO,"saving path: " + file.getAbsolutePath());
        if(!file.exists()){
            if(file.createNewFile()){
                plugin.getLogger().log( Level.INFO,"<create new saving file>");
            }
        }
        List<Object> a = new ArrayList<>();
        a.add(diseasedata.get(uuid));
        a.add(symptomdata.get(uuid));
        FileOutputStream output = new FileOutputStream(file);
        ObjectOutput objectoutput = new ObjectOutputStream(output);
        objectoutput.writeObject(a);
        objectoutput.close();
        output.close();
        return true;
    }

    class Ticker extends BukkitRunnable {
        JavaPlugin plugin;

        int i = 0;

        Ticker(JavaPlugin plugin){
            this.plugin = plugin;
        }

        @Override
        public void run() {
            diseasedata.forEach((uuid, diseases) -> {
                diseases.forEach(disease -> Bukkit.getScheduler().runTask(plugin, () -> {
                    Player player = plugin.getServer().getPlayer(uuid);
                    assert player != null;
                    try{
                        disease.onTick(player);
                    }catch(IllegalArgumentException e){
                        if(player.isOnline()){
                            e.printStackTrace();
                        }
                    }
                }));
            });
        }
    }

    public String toString(int a) {
        return a == 0 ? diseasedata.toString() :  symptomdata.toString();
    }
}
