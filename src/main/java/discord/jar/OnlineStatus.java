package discord.jar;

public enum OnlineStatus
{
    ONLINE, AWAY, OFFLINE, DND, UNKNOWN;

    public static OnlineStatus fromName(String name)
    {
        switch (name.toLowerCase())
        {
            case "online":
                return ONLINE;
            case "idle":
                return AWAY;
            case "offline":
                return OFFLINE;
            case "dnd":
                return DND;
            default:
                return UNKNOWN;
        }
    }
}
