package com.group.bustravel;

/**
 * @data 19:37 26/02/2023
 * @name Stop
 * @introduce
 */
public class Stop {
    static private String stopName;
    static private String location;
    Stop(String stopName, String location){
        this.stopName = stopName;
        this.location = location;
    }

    public String getName(){
        return stopName;
    }

    public String getLocation(){
        return location;
    }
}
