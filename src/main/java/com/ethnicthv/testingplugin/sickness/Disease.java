package com.ethnicthv.testingplugin.sickness;

import com.ethnicthv.testingplugin.sickness.symptom.Symptom;
import org.bukkit.entity.Player;

import java.util.List;

public interface Disease {
    void onTick(Player player);
    String getName();
    boolean isBad();
    boolean isCurable();
    boolean isInfectious();
    List<Symptom> getSymptoms();
}
