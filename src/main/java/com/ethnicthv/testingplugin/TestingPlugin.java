package com.ethnicthv.testingplugin;

import com.ethnicthv.testingplugin.manager.Manager;
import com.ethnicthv.testingplugin.sickness.disease.Benh;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;


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
        this.getCommand("test").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                if(sender instanceof Player){
                    manager.addDisease(((Player) sender).getUniqueId(), new Benh());
                    return true;
                }
                return false;
            }
        });
        this.getCommand("datalist").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                if(sender instanceof Player){
                    ((Player) sender).sendMessage(manager.toString());
                    return true;
                }
                return false;
            }
        });
    }
}
