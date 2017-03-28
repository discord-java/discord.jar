package discord.jar;


import org.json.JSONObject;

public class ChannelRemove implements Poll
{
    private DiscordAPIImpl api;

    public ChannelRemove(DiscordAPIImpl api)
    {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server)
    {
        Group group = server.getGroupById(content.getString("id"));
        server.getGroups().remove(group);
        api.getEventManager().executeEvent(new ChannelDeletedEvent(group));
    }
}
