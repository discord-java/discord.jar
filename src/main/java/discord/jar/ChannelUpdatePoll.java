package discord.jar;


import org.json.JSONObject;

public class ChannelUpdatePoll implements Poll {
    private DiscordAPIImpl api;

    public ChannelUpdatePoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        GroupImpl group = (GroupImpl) server.getGroupById(content.getString("id"));
        group.setName(content.getString("name")); 										//TODO: Fix this shit @Austin  
        api.getEventManager().executeEvent(new ChannelUpdatedEvent(group));
    }
}
