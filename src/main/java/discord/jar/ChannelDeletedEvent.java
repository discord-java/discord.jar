package discord.jar;

public class ChannelDeletedEvent {
    private final Group group;

    public Group getGroup() {
        return this.group;
    }

    public ChannelDeletedEvent(final Group group) {
        this.group = group;
    }
}
