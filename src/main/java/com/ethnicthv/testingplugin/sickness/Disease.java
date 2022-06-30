package com.ethnicthv.testingplugin.sickness;

import java.io.Serializable;

public abstract class Disease implements Serializable {
    private String NAME;
    private boolean isBad, isCurable, isInfectious;
    private int damage = 1;
    private int period = 3000;

    public Disease(String NAME, boolean isBad, boolean isCurable, boolean isInfectious){
        this.NAME = NAME;
        this.isBad = isBad;
        this.isCurable = isCurable;
        this.isInfectious = isInfectious;
    }

    //What this sickness do

    /**
     * The
     */
    void onTick(){

    }

    /**
     * Get the Name of this Disease
     * @return Name of this Disease
     */
    String getName(){
        return NAME;
    }

    //
    /**
     *Is dealling damage
     * @return -true if this deal damage
     *         -false if this don't deal dam
     */
    boolean isBad(){
        return isBad;
    }

    //Is curable
    boolean isCurable(){
        return isCurable;
    }

    //Is infectious
}
