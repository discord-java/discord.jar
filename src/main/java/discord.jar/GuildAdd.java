package discord.jar;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class GuildAdd implements Poll
{
    private DiscordAPIImpl api;

    public GuildAdd(DiscordAPIImpl api)
    {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server)
    {
        ReadyPoll poll = new ReadyPoll(api);
        ServerImpl serverI = new ServerImpl(content.getString("id"), api);

        serverI.setName(content.getString("name"));
        serverI.setLocation(content.getString("region"));
        serverI.setCreatorId(content.getString("owner_id"));
        serverI.setAvatar(content.isNull("icon") ? "" : "https://cdn.discordapp.com/icons/" + serverI.getId() + "/" +
                content.getString("icon") + ".jpg");

        List<GroupUser> users = poll.getGroupUsersFromJson(content, poll.getRoles(content.getJSONArray("roles")));
        users = poll.updateOnlineStatus(users, content.getJSONArray("presences"));

        serverI.getConnectedClients().addAll(users);

        JSONArray channels = content.getJSONArray("channels");
        for (int ia = 0; ia < channels.length(); ia++)
        {
            JSONObject channel = channels.getJSONObject(ia);

            if (!channel.getString("type").equals("text"))
                continue;

            GroupImpl group = new GroupImpl(channel.getString("id"), channel.getString("id"), serverI, api);
            group.setName(channel.getString("name"));
            serverI.getGroups().add(group);
        }

        api.getAvailableServers().add(serverI);

        api.getEventManager().executeEvent(new AddedToServer(serverI));
    }
}
