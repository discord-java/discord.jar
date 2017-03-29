package discord.jar;


import org.json.JSONObject;

public class StatusPoll implements Poll {
    private DiscordAPIImpl api;

    public StatusPoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        try {
            String id = content.getString("guild_id");
            String authorId = content.getJSONObject("user").getString("id");

            Group a = api.getGroupById(id);

            if (a == null) {
                api.log("I think I came online or offline... ignoring.");
                return;
            }

            UserImpl user = (UserImpl) a.getServer().getGroupUserById(authorId).getUser();

            String game = ((content.isNull("game_id")) || (content.get("game_id") == null)) ? "ready to play" :
                    GameIdUtils.getGameFromId(content.getInt("game_id"));
            OnlineStatus status = OnlineStatus.fromName(content.getString("status"));


            user.setGame(game);
            user.setOnlineStatus(status);

            UserOnlineStatusChangedEvent event = new UserOnlineStatusChangedEvent(user, status, game);
            api.getEventManager().executeEvent(event);

        } catch (Exception e) {
            api.log("Failed to process message:\n >" + content);
            e.printStackTrace();
        }
    }
}
