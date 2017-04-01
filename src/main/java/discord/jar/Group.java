package discord.jar;


import java.util.List;

public interface Group {
    /**
    * Gets the ID of the Group
    *
    * @return The ID
    */
    String getId();

    /**
    * Gets the name of the Group
    *
    * @return The name
    */
    String getName();

    /**
    * Gets the overlapping server the Group is in
    *
    * @return The server
    */
    Server getServer();

    /**
    * Gets the message history of the group
    *
    * @return The MessageHistory
    */
    MessageHistory getMessageHistory();

    /**
    * Sends a message to the Group
    *
    * @param message The string to send
    * @return The Message sent
    */
    Message sendMessage(String message);

    /**
    * Sends a message to the Group
    *
    * @param message The message object to send
    * @return The Message sent
    */
    Message sendMessage(Message message);

    Webhook getWebhookById(String id);

    Webhook getWebhookByName(String name);

    Webhook createWebhook(String name, String avatar);

    List<Webhook> getWebhooks();
}
