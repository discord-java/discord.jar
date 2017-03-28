package discord.jar;

import lombok.Getter;
import lombok.Setter;


public class UserImpl implements User, Talkable
{
    @Getter
    @Setter
    private String username;
    @Getter
    private String id;
    @Getter
    private String cid;
    @Getter
    @Setter
    private String avatar;
    @Getter
    @Setter
    private String avatarId;
    @Getter
    @Setter
    private String game;
    @Getter
    @Setter
    private OnlineStatus onlineStatus;
    private DiscordAPIImpl api;

    public UserImpl(String username, String id, String cid, DiscordAPIImpl api)
    {
        this.api = api;
        this.id = id;
        this.cid = cid;
        this.username = username;

        if (!api.getUserGroups().containsKey(id))
        {
            GroupImpl group = new GroupImpl(id, cid, null, api);
            group.setName(username);
            api.getUserGroups().put(id, group);
        }
    }

    @Override
    public String toString()
    {
        return username;
    }

    @Override
    public Group getGroup()
    {
        return api.getUserGroups().get(id);
    }

    @Override
    public boolean equals(Object a)
    {
        return ((a instanceof String) && ((a.equals(id)) || (a.equals(cid))));
    }
}
