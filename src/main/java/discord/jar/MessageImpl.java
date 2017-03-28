package discord.jar;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageImpl implements Message {
    private String message;
    private String id;
    private User sender;
    private String groupId;
    private JSONArray mentions = new JSONArray();
    private boolean edited = false;
    private DiscordAPIImpl api;

    public MessageImpl(String message, String id, String groupId, DiscordAPIImpl api) {
        this.message = message;
        this.id = id;
        this.groupId = groupId;
        this.api = api;
    }

    public MessageImpl(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

    public void editMessage(String edit) {
        edited = true;
        message = edit;
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.PATCH);
        pb.setData(new JSONObject().put("content", StringEscapeUtils.escapeJson(edit)).put("mentions", mentions).toString());
        pb.setUrl("https://discordapp.com/api/channels/" + groupId + "/messages/" + id);
        pb.makeRequest();
    }

    public void deleteMessage() {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.DELETE);
        pb.setUrl("https://discordapp.com/api/channels/" + groupId + "/messages/" + id);
        pb.makeRequest();
    }

    public void applyUserTag(String username, Group server) {
        GroupUser gp = server.getServer().getGroupUserByUsername(username);
        if (gp == null) return;
        message = message.replace("@" + username, "<@" + gp.getUser().getId() + ">");
        mentions.put(gp.getUser().toString());
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getId() {
        return this.id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public User getSender() {
        return this.sender;
    }

    public void setSender(final User sender) {
        this.sender = sender;
    }

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(final String groupId) {
        this.groupId = groupId;
    }

    public JSONArray getMentions() {
        return this.mentions;
    }

    public void setMentions(final JSONArray mentions) {
        this.mentions = mentions;
    }

    public boolean isEdited() {
        return this.edited;
    }

    public void setEdited(final boolean edited) {
        this.edited = edited;
    }
}
