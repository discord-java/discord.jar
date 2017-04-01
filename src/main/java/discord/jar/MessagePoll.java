package discord.jar;


import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONObject;

public class MessagePoll implements Poll {
    private DiscordAPIImpl api;

    public MessagePoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        try {
            String id = content.getString("channel_id");
            String authorId = content.getJSONObject("author").getString("id");

            Group group = api.getGroupById(id);
            User user = api.getUserById(authorId);

            group = (group == null) ? api.getGroupById(authorId) : group;
            user = (user == null) ? api.getBlankUser() : user;

            String msgContent = StringEscapeUtils.unescapeJson(content.getString("content"));
            String msgId = content.getString("id");

            String webhookId = content.has("webhook_id") ? !content.isNull("webhook_id") ? content.getString("webhook_id") : null : null;
            if (webhookId != null && content.has("author")) {
                JSONObject author = content.getJSONObject("author");
                user = new UserImpl(author.getString("username"), webhookId, webhookId, api);
                ((UserImpl) user).setAvatar(author.getString("avatar"));
            }

            MessageImpl msg = new MessageImpl(msgContent, msgId, id, webhookId, api);
            msg.setSender(user);

            if (content.has("embed"))
                msg.addEmbed(new Embed(content.getJSONObject("embed")));

            if (content.has("embeds")) {
                for (Object obj : content.getJSONArray("embeds"))
                    msg.addEmbed(new Embed((JSONObject) obj));
            }

            if (!content.isNull("edited_timestamp"))
                msg.setEdited(true);

            GroupUser gUser = (group.getServer() == null || webhookId != null) ? new GroupUser(user, "User", user.getId()) : group
                    .getServer().getGroupUserById(authorId);

            api.getEventManager().executeEvent(new UserChatEvent(group, gUser, msg));
        } catch (Exception e) {
            api.log("Failed to process message:\n >" + content);
        }
    }
}
