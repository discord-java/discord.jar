package discord.jar;

public class UserBannedEvent {
    private final Server server;
    private final GroupUser groupUser;

    public Server getServer() {
        return this.server;
    }

    public GroupUser getGroupUser() {
        return this.groupUser;
    }

    public UserBannedEvent(final Server server, final GroupUser groupUser) {
        this.server = server;
        this.groupUser = groupUser;
    }
}
