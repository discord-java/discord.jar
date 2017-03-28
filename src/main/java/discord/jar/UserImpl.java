package discord.jar;

public class UserImpl implements User, Talkable {
    private String username;
    private String id;
    private String cid;
    private String avatar;
    private String avatarId;
    private String game;
    private OnlineStatus onlineStatus;
    private DiscordAPIImpl api;

    public UserImpl(String username, String id, String cid, DiscordAPIImpl api) {
        this.api = api;
        this.id = id;
        this.cid = cid;
        this.username = username;
        if (!api.getUserGroups().containsKey(id)) {
            GroupImpl group = new GroupImpl(id, cid, null, api);
            group.setName(username);
            api.getUserGroups().put(id, group);
        }
    }

    @Override
    public String toString() {
        return username;
    }

    @Override
    public Group getGroup() {
        return api.getUserGroups().get(id);
    }

    @Override
    public boolean equals(Object a) {
        return ((a instanceof String) && ((a.equals(id)) || (a.equals(cid))));
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getId() {
        return this.id;
    }

    public String getCid() {
        return this.cid;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public String getAvatarId() {
        return this.avatarId;
    }

    public void setAvatarId(final String avatarId) {
        this.avatarId = avatarId;
    }

    public String getGame() {
        return this.game;
    }

    public void setGame(final String game) {
        this.game = game;
    }

    public OnlineStatus getOnlineStatus() {
        return this.onlineStatus;
    }

    public void setOnlineStatus(final OnlineStatus onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
}
