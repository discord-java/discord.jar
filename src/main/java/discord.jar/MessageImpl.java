package discord.jar;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class MessageImpl implements Message
{
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private String id;
    @Getter
    @Setter
    private User sender;
    @Getter
    @Setter
    private String groupId;
    @Getter
    @Setter
    private JSONArray mentions = new JSONArray();
    @Getter
    @Setter
    private boolean edited = false;
    private DiscordAPIImpl api;

    public MessageImpl(String message, String id, String groupId, DiscordAPIImpl api)
    {
        this.message = message;
        this.id = id;
        this.groupId = groupId;
        this.api = api;
    }

    public MessageImpl(String message)
    {
        this.message = message;
    }

    @Override
    public String toString()
    {
        return message;
    }

    public void editMessage(String edit)
    {
        edited = true;
        message = edit;
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.PATCH);
        pb.setData(new JSONObject().put("content", StringEscapeUtils.escapeJson(edit))
                .put("mentions", mentions).toString());
        pb.setUrl("https://discordapp.com/api/channels/" + groupId + "/messages/" + id);
        pb.makeRequest();
    }

    public void deleteMessage()
    {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.DELETE);
        pb.setUrl("https://discordapp.com/api/channels/" + groupId + "/messages/" + id);
        pb.makeRequest();
    }

    public void applyUserTag(String username, Group server)
    {
        GroupUser gp = server.getServer().getGroupUserByUsername(username);
        if (gp == null)
            return;
        message = message.replace("@" + username, "<@" + gp.getUser().getId() + ">");
        mentions.put(gp.getUser().toString());
    }

}
