package discord.jar;


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
}
