package discord.jar;

public class UserChatEvent {
    private final Group group;
    private final GroupUser user;
    private Message msg;

    public Server getServer() {
        return group.getServer();
    }

    public Group getGroup() {
        return this.group;
    }

    public GroupUser getUser() {
        return this.user;
    }

    public Message getMsg() {
        return this.msg;
    }

    public UserChatEvent(final Group group, final GroupUser user, final Message msg) {
        this.group = group;
        this.user = user;
        this.msg = msg;
    }
}
