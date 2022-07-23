package com.ethnicthv.testingplugin.sickness.symptom;

import org.bukkit.entity.Player;

public class TrieuChung extends AbstractSymptom{
    @Override
    public boolean doEffect(Player... player) {
        for(Player p : player){
            p.sendMessage("---a");
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
