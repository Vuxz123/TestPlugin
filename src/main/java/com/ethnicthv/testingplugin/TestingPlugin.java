package com.ethnicthv.testingplugin;

import com.ethnicthv.testingplugin.manager.Manager;
import com.ethnicthv.testingplugin.sickness.disease.Benh;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestingPlugin extends JavaPlugin {
    Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        manager = new Manager(this);

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
                sender.sendMessage(manager.toString());
                return true;
            }
            return false;
        });
    }
}
