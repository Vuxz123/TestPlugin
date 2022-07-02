package com.ethnicthv.testingplugin.sickness;

import com.ethnicthv.testingplugin.sickness.symptom.Symptom;

import java.util.List;

public interface Disease {
    void onTick();
    String getName();
    boolean isBad();
    boolean isCurable();
    boolean isInfectious();
    List<Symptom> getSymptoms();
}
