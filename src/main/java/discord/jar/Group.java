package discord.jar;


import java.util.List;

public interface Group {
    String getId();

    String getName();

    Server getServer();

    MessageHistory getMessageHistory();

    Message sendMessage(String message);

    Message sendMessage(Message message);

    Webhook getWebhookById(String id);

    Webhook getWebhookByName(String name);

    Webhook createWebhook(String name, String avatar);

    List<Webhook> getWebhooks();
}
