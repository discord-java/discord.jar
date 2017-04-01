package discord.jar;

import java.util.List;
import java.util.Map;

public interface DiscordAPI {
    DiscordAPI login() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException;

    DiscordAPI login(String token) throws BadUsernamePasswordException,
            DiscordFailedToConnectException;

    /**
    * Stops the DiscordAPI instance
    */
    void stop();

    /**
    * Sets whether or not log messages are allowed
    *
    * @param val Whether or not they are allowed
    */
    void setAllowLogMessages(boolean val);

    /**
    * Sets whether debug mode is on or not
    *
    * @param val If debug mode is on
    */
    void setDebugMode(boolean val);

    /**
    * If the instance is in debug mode or not
    *
    * @return True if it is, false otherwise
    */
    boolean isDebugMode();
   
    /**
    * If the instance allows log messages or not
    *
    * @return True if it does, false otherwise
    */
    boolean isAllowLogMessages();

    /**
    * Returns if the instance is loaded or not
    *
    * @return True if it is loaded, false otherwise
    */
    boolean isLoaded();
    
    /**
    * If the instance is stopped or not
    *
    * @return True if it is, false otherwise
    */
    boolean isStopped();

    /**
    * Attempts to join a server by ID
    *
    * @param id The ID to join
    * @return True if successful, false otherwise
    */
    Boolean joinInviteId(String id);

    /**
    * Gets the EventManager for the instance
    *
    * @return The EventManager
    */
    EventManager getEventManager();

    /**
    * Gets a user by their ID
    *
    * @param id The ID to look for
    * @return The User
    */
    User getUserById(String id);

    /**
    * Gets a user by their Username
    *
    * @param id The name to look for
    * @return The user
    */
    User getUserByUsername(String id);

    /**
    * Gets a group by the ID
    *
    * @param id The ID to look for
    * @return The Group
    */
    Group getGroupById(String id);

    /**
    * Gets a server by the ID
    *
    * @param id The ID to look for
    * @return The server
    */
    Server getServerById(String id);

    /**
    * Gets every user the bot can DM
    *
    * @return The list of users
    */
    List<User> getAvailableDms();

    /**
    * Gets every avaliable server
    *
    * @return The list of servers
    */
    List<Server> getAvailableServers();

    /**
    * Gets the DMs of the users the bot has access to
    *
    * @return The map of User IDs and groups
    */
    Map<String, Group> getUserGroups();

    /**
    * Gets the bot info
    *
    * @return The info
    */
    SelfData getSelfInfo();

    /**
    * Gets the account manager for this instance
    *
    * @return The account manager
    */
    AccountManager getAccountManager();
}
