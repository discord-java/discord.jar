package discord.jar;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class WebhookImpl implements Webhook {
    private String name;
    private String avatar;
    private String token;
    private String id;
    private String groupId;
    private DiscordAPIImpl api;

    public WebhookImpl(String name, String avatar, String token, String id, String groupId, DiscordAPIImpl api) {
        this.name = name;
        this.avatar = avatar;
        this.token = token;
        this.id = id;
        this.groupId = groupId;
        this.api = api;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getGroupId() {
        return groupId;
    }

    @Override
    public void changeName(String name) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.PATCH);
        pb.setData(new JSONObject().put("name", StringEscapeUtils.escapeJson(name)).toString());
        pb.setUrl("https://discordapp.com/api/webhooks/" + id);
        pb.makeRequest();
    }

    @Override
    public void changeAvatar(String avatar) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.PATCH);
        pb.setData(new JSONObject().put("avatar", StringEscapeUtils.escapeJson(avatar)).toString());
        pb.setUrl("https://discordapp.com/api/webhooks/" + id);
        System.out.println(pb.makeRequest());
    }

    @Override
    public void execute(String content) {
        execute(content, getName(), getAvatarUrl(), false);
    }

    @Override
    public void execute(String content, String username) {
        execute(content, username, getAvatarUrl(), false);
    }

    @Override
    public void execute(String content, String username, String avatarUrl) {
        execute(content, username, avatarUrl, false);
    }

    @Override
    public void execute(String content, String username, String avatarUrl, boolean tts) {
        execute(content, new Embed[0], username, avatarUrl, tts);
    }

    @Override
    public void execute(Embed[] embeds) {
        execute(embeds, getName(), getAvatarUrl());
    }

    @Override
    public void execute(Embed[] embeds, String username) {
        execute(embeds, username, getAvatarUrl());
    }

    @Override
    public void execute(Embed[] embeds, String username, String avatarUrl) {
        execute("", embeds, username, avatarUrl, false);
    }

    public void execute(String content, Embed[] embeds, String username, String avatarUrl, boolean tts) {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setType(RequestType.POST);
        JSONArray jsonEmbeds = new JSONArray();
        for (Embed embed : embeds) jsonEmbeds.put(embed.toJson());
        pb.setData(new JSONObject().put("content", content).put("embeds", jsonEmbeds).put("username", username).put("avatar_url", avatarUrl).put("tts", tts).toString());
        pb.setUrl("https://discordapp.com/api/webhooks/" + id + "/" + token);
        System.out.println(pb.makeRequest());
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public String getAvatarUrl() {
        return avatar == null ? "https://cdn.discordapp.com/embed/avatars/0.png" : "https://cdn.discordapp.com/avatars/" + id + "/" + avatar;
    }

    public String getToken() {
        return token;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
