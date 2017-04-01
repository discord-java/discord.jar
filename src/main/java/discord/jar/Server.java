package discord.jar;


import java.util.List;

public interface Server {
    
    /**
    * Gets the ID of the server
    *
    * @return The ID
    */
    String getId();
    
    /**
    * Gets the name of the server
    *
    * @return The name
    */
    String getName();

    /**
    * Gets the location of the server
    *
    * @return The location
    */
    String getLocation();

    /**
    * Gets the ID of the creator of the server
    *
    * @return The ID
    */
    String getCreatorId();
    
    /**
    * Gets the avatar URL of the server
    *
    * @return The URL
    */
    String getAvatar();
    
    /**
    * Gets the GroupUser by the ID specified
    *
    * @param id The ID to look for
    * @return The GroupUser
    */    
    GroupUser getGroupUserById(String id);
    
    /**
    * Gets the GroupUser by the name specified
    *
    * @param username The name to look for
    * @return The GroupUser
    */    
    GroupUser getGroupUserByUsername(String username);
    
    /**
    * Gets list of GroupUser's by connected clients
    *
    * @return The GroupUser list
    */
    List<GroupUser> getConnectedClients();
    
    /**
    * Gets list of Groups
    *
    * @return The Group list
    */    
    List<Group> getGroups();

    //List<VoiceGroupImpl> getVoiceGroups();

    /**
    * Kicks a user from the server
    *
    * @param user The user to kick
    */
    void kick(String user);
    
    /**
    * Bans a user from the server
    *
    * @param user The user to ban
    */
    void ban(String user);

    /**
    * Broadcasts a message to the server
    *
    * @param message The message to broadcast
    */
    void bc(String message);

    /**
    * Gets a group on the server by ID
    *
    * @param id The ID to look for
    * @return The Group
    */
    Group getGroupById(String id);

    Webhook getWebhookById(String id);

    Webhook getWebhookByName(String name);

    List<Webhook> getWebhooks();

    List<Webhook> getWebhooksForGroup(Group group);

    List<Webhook> getWebhooksForGroup(String groupID);
}
