package discord.jar;

public class UserKickedEvent {
    private final Server server;
    private final GroupUser groupUser;

    public Server getServer() {
        return this.server;
    }

    public GroupUser getGroupUser() {
        return this.groupUser;
    }

    public UserKickedEvent(final Server server, final GroupUser groupUser) {
        this.server = server;
        this.groupUser = groupUser;
    }
}
