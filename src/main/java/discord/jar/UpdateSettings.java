package discord.jar;


import org.json.JSONObject;

public class UpdateSettings implements Poll {
    private DiscordAPIImpl api;

    public UpdateSettings(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        api.getSelfInfo().setUsername(content.getString("username"));
        api.getSelfInfo().setEmail(content.getString("email"));
        api.getSelfInfo().setAvatarId("https://cdn.discordapp.com/avatars/" + api.getSelfInfo().getId() + "/" +
                (content.isNull("avatar") ? "" : content.getString("avatar")) + ".jpg");
        api.getSelfInfo().setAvatar(content.isNull("avatar") ? "" : content.getString("avatar"));
    }
}
