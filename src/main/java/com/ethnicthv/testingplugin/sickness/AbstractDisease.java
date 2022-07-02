package com.ethnicthv.testingplugin.sickness;

import com.ethnicthv.testingplugin.sickness.symptom.Symptom;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractDisease implements Serializable,Disease {
    private String NAME;
    private boolean isBad, isCurable, isInfectious;
    private int damage = 1;
    private int period = 3000;
    private List<Symptom> symptoms;

    public AbstractDisease(String NAME, boolean isBad, boolean isCurable, boolean isInfectious){
        this.NAME = NAME;
        this.isBad = isBad;
        this.isCurable = isCurable;
        this.isInfectious = isInfectious;
        symptoms = new ArrayList<>();
    }

    //What this sickness do
    /**
     * The
     */
    @Override
    public void onTick(){

    }

    /**
     * Get the Name of this Disease
     * @return Name of this Disease
     */
    @Override
    public String getName(){
        return NAME;
    }

    //
    /**
     *Is dealing damage by time
     * @return -true if this deal damage
     *         -false if this don't deal dam
     */
    @Override
    public boolean isBad(){
        return isBad;
    }

    /**
     * Some Disease cannot be cured by drinking milk
     * Mark this as false to disable that
     * @return if this can be cured by drinking milk
     */
    //Is curable
    @Override
    public boolean isCurable(){
        return isCurable;
    }

    //Is infectious

    /**
     * Some Disease can be infectious and infect other player
     * Mark this as false to disable that
     * @return if this disease is infectious
     */
    @Override
    public boolean isInfectious() {
        return isInfectious;
    }

    /**
     * List of Symptom this disease has
     * @return the list of Symptom
     */
    @Override
    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    /**
     * Set the list of symptom this disease has
     * @param symptoms
     */
    public void setSymptoms(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    /**
     * Set the period for each time this disease affect player
     * @param period
     */
    public void setPeriod(int period) {
        this.period = period;
    }

    /**
     * set the damage this disease deal to player foreach period
     * set it to 0 to disable this
     * @param damage
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }
}
