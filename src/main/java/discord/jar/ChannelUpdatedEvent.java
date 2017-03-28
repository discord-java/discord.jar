package discord.jar;

public class ChannelUpdatedEvent {
    private final Group group;

    public Group getGroup() {
        return this.group;
    }

    public ChannelUpdatedEvent(final Group group) {
        this.group = group;
    }
}
