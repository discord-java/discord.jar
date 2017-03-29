package discord.jar;


import org.json.JSONObject;

public class BanPoll implements Poll {
    private DiscordAPIImpl api;

    public BanPoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        JSONObject user = content.getJSONObject("user");
        GroupUser gUser = server.getGroupUserById(user.getString("id"));
        server.getConnectedClients().remove(gUser);
        api.getEventManager().executeEvent(new UserBannedEvent(server, gUser));
    }
}
