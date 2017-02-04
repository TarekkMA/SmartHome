package com.tmaprojects.smarthome.models;

/**
 * Created by TarekMA on 1/31/17.
 * facebook/tarekkma1
 */

public class SwitchesGroup {
    private String name;
    private int color;
    private String id;

    public SwitchesGroup() {
    }

    public SwitchesGroup(String name, int color, String id) {
        this.name = name;
        this.color = color;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
