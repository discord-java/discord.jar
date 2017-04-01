package discord.jar;

import org.json.JSONObject;

import java.util.List;

public class GroupImpl implements Group, Talkable {
    private String cid;
    private String id;
    private String name;
    private DiscordAPIImpl api;
    private Server server;

    public GroupImpl(String id, String cid, Server server, DiscordAPIImpl api) {
        this.api = api;
        this.id = id;
        this.name = id;
        this.cid = cid;
        this.server = server;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public Server getServer() {
        return server;
    }

    @Override
    public MessageHistory getMessageHistory() {
        //if (api.getHistoryContainer().containsKey(id))
        //    api.getHistoryContainer().put(id, new MessageHistory());
        //return api.getHistoryContainer().get(id);
        return null;
    }

    @Override
    public Message sendMessage(String message) {
        return sendMessage(new MessageImpl(message, id, id, null, api));
    }

    @Override
    public Message sendMessage(Message messageInterface) {
        if (server == null) updateId();
        MessageImpl message = (MessageImpl) messageInterface;
        message.setId(String.valueOf(System.currentTimeMillis()));
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.POST);
        pb.setData(new JSONObject().put("content", message.getMessage()).put("embed", message.getEmbeds().isEmpty() ? new JSONObject() : message.getEmbeds().get(0).toJson()).put("tts", false).toString());
        pb.setUrl("https://discordapp.com/api/channels/" + id + "/messages");
        String a = pb.makeRequest();
        if (a != null) return new MessageImpl(message.getMessage(), new JSONObject(a).getString("id"), id, null, api);
        return message;
    }

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
    public Webhook createWebhook(String name, String avatar) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.POST);
        pb.setData(new JSONObject().put("name", name).put("avatar", avatar).toString());
        pb.setUrl("https://discordapp.com/api/channels/" + id + "/webhooks");
        String a = pb.makeRequest();
        if (a != null) {
            JSONObject response = new JSONObject(a);
            return new WebhookImpl(response.getString("name"), response.isNull("avatar") ? null : response.getString("avatar"), response.getString("token"), response.getString("id"), response.getString("channel_id"), api);
        }
        return null;
    }

    @Override
    public List<Webhook> getWebhooks() {
        return getServer().getWebhooksForGroup(id);
    }

    private void updateId() {
        if (id.equals(api.getSelfInfo().getId())) return;
        PacketBuilder pb = new PacketBuilder(api);
        pb.setUrl("https://discordapp.com/api/users/" + api.getSelfInfo().getId() + "/channels");
        pb.setType(RequestType.POST);
        pb.setData(new JSONObject().put("recipient_id", id).toString());
        String a = pb.makeRequest();
        if (a == null) return;
        id = new JSONObject(a).getString("id");
    }

    public String getCid() {
        return this.cid;
    }

    public void setCid(final String cid) {
        this.cid = cid;
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
}
