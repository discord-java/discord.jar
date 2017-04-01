package discord.jar;

public interface Webhook {
    String getName();

    String getId();

    String getGroupId();

    String getAvatar();

    String getAvatarUrl();

    void changeName(String name);

    void changeAvatar(String avatar);

    void execute(String content);

    void execute(String content, String username);

    void execute(String content, String username, String avatarUrl);

    void execute(String content, String username, String avatarUrl, boolean tts);

    void execute(Embed[] embeds);

    void execute(Embed[] embeds, String username);

    void execute(Embed[] embeds, String username, String avatarUrl);

    void execute(String content, Embed[] embeds, String username, String avatarUrl, boolean tts);
}
