package discord.jar;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class AccountManagerImpl implements AccountManager {
    private DiscordAPIImpl discord;
    private String username;
    private String password;
    private String email;
    private String avatar;
    private String newpass;
    public boolean isOnline = true;

    public AccountManagerImpl(DiscordAPIImpl api) {
        discord = api;
    }

    @Override
    public void setOnlineStatus(boolean online) {
        isOnline = online;
        if (online) {
            JSONObject obj = new JSONObject().put("op", 3)
                    .put("d", new JSONObject()
                            .put("idle_since", JSONObject.NULL) //system millis
                            .put("game_id", JSONObject.NULL));


            discord.getRequestManager().getSocketClient().send(obj.toString());
        } else {
            JSONObject obj = new JSONObject().put("op", 3)
                    .put("d", new JSONObject()
                            .put("idle_since", ((DiscordAPIImpl) discord).getStartedTime()) //system millis
                            .put("game_id", JSONObject.NULL));

            discord.getRequestManager().getSocketClient().send(obj.toString());
        }
    }

    @Override
    public void setDisplayName(String displayName) {
        updateLocalVars();
        this.username = displayName;
        updateEdits();
    }

    @Override
    public void setAvatar(InputStream is) throws IOException {
        updateLocalVars();
        this.avatar = "data:image/jpeg;base64," + StringUtils.newStringUtf8(Base64.encodeBase64(IOUtils.toByteArray
                (is), false));
        updateEdits();
    }

    @Override
    public void changePass(String pass) {
        updateLocalVars();
        this.newpass = pass;
        updateEdits();
    }

    @Override
    public void changeEmail(String email) {
        updateLocalVars();
        this.email = email;
        updateEdits();
    }

    private String getPassword() {
        return discord.getLoginTokens().getPassword();
    }

    private void updateLocalVars() {
        username = discord.getSelfInfo().getUsername();
        password = discord.getLoginTokens().getPassword();
        email = discord.getSelfInfo().getEmail();
        avatar = discord.getSelfInfo().getAvatarId();
        newpass = null;
    }

    private void updateEdits() {
        PacketBuilder pb = new PacketBuilder(discord);
        pb.setUrl("https://discordapp.com/api/users/@me");
        pb.setType(RequestType.PATCH);
        pb.setData(getJSON());
        pb.makeRequest();

        discord.getSelfInfo().setUsername(username);
        discord.getLoginTokens().setPassword(newpass == null ? password : newpass);
        discord.getSelfInfo().setEmail(email);
    }

    private String getJSON() {
        JSONObject json = new JSONObject()
                .put("username", username)
                .put("email", email)
                .put("password", password)
                .put("new_password", newpass)
                .put("avatar", avatar);
        return json.toString();
    }
}
