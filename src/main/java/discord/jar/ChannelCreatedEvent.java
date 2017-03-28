package discord.jar;

public class ChannelCreatedEvent {
    private final Group group;

    public Group getGroup() {
        return this.group;
    }

    public ChannelCreatedEvent(final Group group) {
        this.group = group;
    }
}
