package discord.jar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements Server {
    private String id;
    private String name;
    private String location;
    private String creatorId;
    private String avatar;
    private String token;
    private String server;
    private List<GroupUser> connectedClients = new ArrayList<>();
    private List<Group> groups = new ArrayList<>();
    private List<Webhook> webhooks = new ArrayList<>();
    private DiscordAPIImpl api;

    public ServerImpl(String id, DiscordAPIImpl api) {
        this.api = api;
        this.id = id;

        loadWebhooks();
    }

    public String toString() {
        return id;
    }

    @Override
    public GroupUser getGroupUserById(String id) {
        for (GroupUser user : connectedClients) if (user.getUser().getId().equals(id)) return user;
        return null;
    }

    @Override
    public Group getGroupById(String id) {
        for (Group group : getGroups()) if (group.getId().equals(id)) return group;
        return null;
    }

    @Override
    public Webhook getWebhookById(String id) {
        for (Webhook webhook : getWebhooks()) if (webhook.getId().equals(id)) return webhook;
        return null;
    }

    @Override
    public Webhook getWebhookByName(String name) {
        for (Webhook webhook : getWebhooks()) if (webhook.getName().equals(name)) return webhook;
        return null;
    }

    @Override
    public List<Webhook> getWebhooks() {
        return webhooks;
    }

    @Override
    public List<Webhook> getWebhooksForGroup(Group group) {
        return getWebhooksForGroup(group.getId());
    }

    @Override
    public List<Webhook> getWebhooksForGroup(String groupId) {
        List<Webhook> groupWebhooks = new ArrayList<>();
        for (Webhook webhook : getWebhooks()) if (webhook.getGroupId().equals(groupId)) groupWebhooks.add(webhook);
        return groupWebhooks;
    }

    public void loadWebhooks() {
        //Delegate to some form of rate limit handler once it's implemented
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.GET);
        pb.setUrl("https://discordapp.com/api/guilds/" + id + "/webhooks");
        String a = pb.makeRequest();
        if (a != null && pb.getCode() == 200) {
            if (!a.contains("message")) {
                JSONArray webhooks = new JSONArray(a);
                for (Object obj : webhooks) {
                    if (obj instanceof JSONObject) {
                        JSONObject webhook = (JSONObject) obj;
                        this.webhooks.add(new WebhookImpl(webhook.getString("name"), webhook.isNull("avatar") ? null : webhook.getString("avatar"), webhook.getString("token"), webhook.getString("id"), webhook.getString("channel_id"), api));
                    }
                }
            }
        }
    }

    @Override
    public GroupUser getGroupUserByUsername(String id) {
        for (GroupUser user : connectedClients) if (user.getUser().getUsername().equals(id)) return user;
        return null;
    }

    @Override
    public void bc(String message) {
        for (Group group : getGroups()) group.sendMessage(message);
    }

    @Override
    public void kick(String user) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setUrl("https://discordapp.com/api/guilds/" + id + "/members/" + getGroupUserByUsername(user).getUser().getId());
        pb.setType(RequestType.DELETE);
        pb.makeRequest();
    }

    @Override
    public void ban(String user) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setUrl("https://discordapp.com/api/guilds/" + id + "/bans/" + getGroupUserByUsername(user).getUser().getId() + "?delete-message-days=0");
        pb.setType(RequestType.PUT);
        pb.makeRequest();
    }

    public void updateUser(GroupUser user) {
        ArrayList<GroupUser> users = new ArrayList<>();
        for (GroupUser userA : connectedClients)
            if (userA.getUser().getId().equals(user.getUser().getId())) users.add(userA);
        connectedClients.removeAll(users);
        connectedClients.add(user);
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(final String creatorId) {
        this.creatorId = creatorId;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(final String server) {
        this.server = server;
    }

    public List<GroupUser> getConnectedClients() {
        return this.connectedClients;
    }

    public void setConnectedClients(final List<GroupUser> connectedClients) {
        this.connectedClients = connectedClients;
    }

    public List<Group> getGroups() {
        return this.groups;
    }

    public void setGroups(final List<Group> groups) {
        this.groups = groups;
    }
}
