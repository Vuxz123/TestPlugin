package com.ethnicthv.testingplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.ethnicthv.testingplugin.manager.Manager;
import com.ethnicthv.testingplugin.sickness.disease.Benh;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestingPlugin extends JavaPlugin {
    private ProtocolManager protocolManager;
    private Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        manager = new Manager(this);
        protocolManager = ProtocolLibrary.getProtocolManager();

        //testing setup
        test();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        manager.saveWhenServerShutdown();
    }

    public void test(){
        this.getCommand("test").setExecutor((sender, command, label, args) -> {
            if(sender instanceof Player){
                manager.addDisease(((Player) sender).getUniqueId(), new Benh());
                return true;
            }
            return false;
        });
        this.getCommand("datalist").setExecutor((sender, command, label, args) -> {
            if(sender instanceof Player){
                if(args[0].equals("disease")){
                    sender.sendMessage(manager.toString(0));
                    return true;
                }
                sender.sendMessage(manager.toString(1));
                return true;
            }
            return false;
        });
    }
}
