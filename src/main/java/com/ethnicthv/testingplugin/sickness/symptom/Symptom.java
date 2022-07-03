package com.ethnicthv.testingplugin.sickness.symptom;

import org.bukkit.entity.Player;

public interface Symptom {
    /**
     * Run this to run the effect of this symptom to a player or a group of players
     *
     * @return -true if success
     *         -false if fail
     */
    boolean doEffect(Player... player);
}
