package discord.jar;

import java.util.List;
import java.util.Map;

public interface DiscordAPI
{
    DiscordAPI login() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException;

    DiscordAPI login(String email, String password) throws BadUsernamePasswordException,
            DiscordFailedToConnectException;

    void stop();

    void setAllowLogMessages(boolean val);

    void setDebugMode(boolean val);

    boolean isDebugMode();

    boolean isAllowLogMessages();

    boolean isLoaded();

    Boolean joinInviteId(String id);

    EventManager getEventManager();

    User getUserById(String id);

    User getUserByUsername(String id);

    Group getGroupById(String id);

    Server getServerById(String id);

    List<User> getAvailableDms();

    List<Server> getAvailableServers();

    Map<String, Group> getUserGroups();

    SelfData getSelfInfo();

    AccountManager getAccountManager();
}
