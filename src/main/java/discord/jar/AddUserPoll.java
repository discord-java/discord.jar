package discord.jar;

import org.json.JSONObject;

public class AddUserPoll implements Poll {
    private DiscordAPIImpl api;

    public AddUserPoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        JSONObject user = content.getJSONObject("user");

        UserImpl userImpl = new UserImpl(user.getString("username"), user.getString("id"), user.getString("id"), api);
        userImpl.setAvatar(user.isNull("avatar") ? "" : "https://cdn.discordapp.com/avatars/" + server.getId() + "/"
                + user.getString("avatar") + ".jpg");
        userImpl.setAvatarId(user.isNull("avatar") ? "" : server.getId());

        GroupUser gUser = new GroupUser(userImpl, "User", user.getString("discriminator"));

        server.getConnectedClients().add(gUser);

        api.getEventManager().executeEvent(new UserJoinedChat(server, gUser));
    }
}
