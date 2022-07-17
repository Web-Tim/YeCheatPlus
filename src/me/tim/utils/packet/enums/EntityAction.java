package me.tim.utils.packet.enums;

public enum EntityAction {
    INTERACT("INTERACT"),
    ATTACK("ATTACK"),
    INTERACT_AT("INTERACT_AT");

    private String name;
    private EntityAction(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
