package com.ethnicthv.testingplugin.sickness;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public enum InfectionLevel implements Serializable {
    HAZARDOUS(4,0.7f) ,EASILY(3,0.45f), MEDIUM(2,0.2f), HARD(1,0.05f), NONE(0,0f);

    private static final long serialVersionUID = 2L;
    int level;
    float percent;
    InfectionLevel(int level, float percent){
        this.level = level;
        this.percent = percent;
    }

    float toPercent(){
        return percent;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
