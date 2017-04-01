package discord.jar;


import java.util.List;

public interface Server {
    String getId();

    String getName();

    String getLocation();

    String getCreatorId();

    String getAvatar();

    GroupUser getGroupUserById(String id);

    GroupUser getGroupUserByUsername(String username);

    List<GroupUser> getConnectedClients();

    List<Group> getGroups();

    //List<VoiceGroupImpl> getVoiceGroups();

    void kick(String user);

    void ban(String user);

    void bc(String message);

    Group getGroupById(String id);

    Webhook getWebhookById(String id);

    Webhook getWebhookByName(String name);

    List<Webhook> getWebhooks();

    List<Webhook> getWebhooksForGroup(Group group);

    List<Webhook> getWebhooksForGroup(String groupID);
}
