package com.ethnicthv.testingplugin.sickness.symptom;

import org.bukkit.entity.Player;

import java.io.Serializable;

public abstract class AbstractSymptom implements Symptom, Serializable {
    @Override
    public abstract boolean doEffect(Player... player);
}
