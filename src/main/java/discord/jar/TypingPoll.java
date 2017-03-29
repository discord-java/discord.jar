package discord.jar;


import org.json.JSONObject;

public class TypingPoll implements Poll {
    private DiscordAPIImpl api;

    public TypingPoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        String id = content.getString("channel_id");
        String authorId = content.getString("user_id");

        Group group = api.getGroupById(id);
        User user = api.getUserById(authorId);

        group = (group == null) ? api.getGroupById(authorId) : group;
        user = (user == null) ? api.getBlankUser() : user;

        GroupUser gUser = (group.getServer() == null) ? new GroupUser(user, "User", user.getId()) : group.getServer()
                .getGroupUserById(authorId);

        api.getEventManager().executeEvent(new UserTypingEvent(group, gUser));
    }
}
