package discord.jar;

public class UserDeletedMessageEvent {
    private final Group group;
    private final String id;

    public Server getServer() {
        return group.getServer();
    }

    public Group getGroup() {
        return this.group;
    }

    public String getId() {
        return this.id;
    }

    public UserDeletedMessageEvent(final Group group, final String id) {
        this.group = group;
        this.id = id;
    }
}
