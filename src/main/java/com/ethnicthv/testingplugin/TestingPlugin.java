package com.ethnicthv.testingplugin;

import com.ethnicthv.testingplugin.manager.Manager;
import com.ethnicthv.testingplugin.sickness.AbstractDisease;
import com.ethnicthv.testingplugin.sickness.InfectionLevel;
import com.ethnicthv.testingplugin.sickness.symptom.Symptom;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


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
    }

    public void test(){
        this.getCommand("test").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
                if(sender instanceof Player){
                    manager.addDisease(((Player) sender).getUniqueId(), new AbstractDisease("NAME", false, true, InfectionLevel.NONE){});
                }
                return false;
            }
        });
    }
}
