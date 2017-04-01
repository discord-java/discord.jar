package discord.jar;


import java.util.List;

public interface Message {
    String getMessage();

    void setMessage(String message);

    User getSender();

    String getId();

    String getGroupId();

    boolean isEdited();

    void applyUserTag(String username, Group server);

    void deleteMessage();

    void editMessage(String message);

    List<Embed> getEmbeds();

    void setEmbeds(List<Embed> embeds);

    void addEmbed(Embed embed);

    String getWebhookId();
}
