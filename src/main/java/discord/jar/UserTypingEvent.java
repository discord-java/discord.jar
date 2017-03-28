package discord.jar;

public class UserTypingEvent {
    private final Group group;
    private final GroupUser user;

    public Server getServer() {
        return group.getServer();
    }

    public Group getGroup() {
        return this.group;
    }

    public GroupUser getUser() {
        return this.user;
    }

    public UserTypingEvent(final Group group, final GroupUser user) {
        this.group = group;
        this.user = user;
    }
}
