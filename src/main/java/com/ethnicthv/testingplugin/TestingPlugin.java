package com.ethnicthv.testingplugin;

import com.ethnicthv.testingplugin.manager.Manager;
import org.bukkit.plugin.java.JavaPlugin;


public final class TestingPlugin extends JavaPlugin {
    Manager manager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        manager = new Manager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
