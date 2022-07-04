package com.ethnicthv.testingplugin.sickness.disease;

import com.ethnicthv.testingplugin.sickness.AbstractDisease;
import com.ethnicthv.testingplugin.sickness.InfectionLevel;
import com.ethnicthv.testingplugin.sickness.symptom.Symptom;
import com.ethnicthv.testingplugin.sickness.symptom.TrieuChung;

import java.util.ArrayList;
import java.util.List;

public class Benh extends AbstractDisease {
    public Benh() {
        super("NAME", false, true, InfectionLevel.NONE);
        List<Symptom> symptoms = new ArrayList<>();
        symptoms.add(new TrieuChung());
        super.setSymptoms(symptoms);
    }
}
