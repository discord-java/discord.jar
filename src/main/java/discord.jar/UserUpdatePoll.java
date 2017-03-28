package discord.jar;


import org.json.JSONArray;
import org.json.JSONObject;

public class UserUpdatePoll implements Poll
{
    private DiscordAPIImpl api;

    public UserUpdatePoll(DiscordAPIImpl api)
    {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server)
    {
        JSONObject user = content.getJSONObject("user");
        JSONArray rolesArray = content.getJSONArray("roles");
        GroupUser gUser = server.getGroupUserById(user.getString("id"));

        ((UserImpl) gUser.getUser()).setUsername(user.getString("username"));
        ((UserImpl) gUser.getUser()).setAvatarId(user.getString("avatar"));
        ((UserImpl) gUser.getUser()).setAvatar("https://cdn.discordapp.com/avatars/" + api.getSelfInfo().getId() +
                "/" + (user.isNull("avatar") ? "" : user.getString("avatar")) + ".jpg");


        for (int i = 0; i < rolesArray.length(); i++)
        {
            JSONObject roleObj = rolesArray.getJSONObject(i);
            gUser.setRole(roleObj.getString("name"));
        }
    }
}
