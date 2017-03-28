package discord.jar;

public class UserOnlineStatusChangedEvent {
    private final User user;
    private final OnlineStatus status;
    private final String game;

    public User getUser() {
        return this.user;
    }

    public OnlineStatus getStatus() {
        return this.status;
    }

    public String getGame() {
        return this.game;
    }

    public UserOnlineStatusChangedEvent(final User user, final OnlineStatus status, final String game) {
        this.user = user;
        this.status = status;
        this.game = game;
    }
}
