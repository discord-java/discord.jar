package discord.jar;


public interface Group {
    String getId();

    String getName();

    Server getServer();

    MessageHistory getMessageHistory();

    Message sendMessage(String message);

    Message sendMessage(Message message);
}
