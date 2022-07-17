package me.tim.utils.packet.enums;

public enum ClientCommands {
    START_SNEAKING(0),
    STOP_SNEAKING(1),
    LEAVE_BED(2),
    START_SPRINTING(3),
    STOP_SPRINTING(4),
    START_HORSE_JUMP(5),
    STOP_HORSE_JUMP(6),
    OPEN_HORSE_INVENTORY(7),
    START_ELYTRA_FLY(8);

    private int id;
    private ClientCommands(int id)
    {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
