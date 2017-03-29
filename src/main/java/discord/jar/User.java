package discord.jar;

public interface User {
    Group getGroup();

    String getAvatar();

    String getUsername();

    String getId();

    String getGame();

    OnlineStatus getOnlineStatus();
}
