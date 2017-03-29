package discord.jar;


import org.json.JSONObject;

public class DeleteMessagePoll implements Poll {
    private DiscordAPIImpl api;

    public DeleteMessagePoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        try {
            Group group = api.getGroupById(content.getString("channel_id"));
            String id = content.getString("id");
            api.getEventManager().executeEvent(new UserDeletedMessageEvent(group, id));
        } catch (Exception e) {
            api.log("Failed to process message:\n >" + content);
        }
    }
}
