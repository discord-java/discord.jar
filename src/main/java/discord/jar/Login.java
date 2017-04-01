package discord.jar;

import org.json.JSONObject;

public class Login {
    private String token;

    public void process(DiscordAPIImpl api) throws BadUsernamePasswordException, DiscordFailedToConnectException {
        api.log("Attempting to login!");
        api.log("Logged in and starting session!");
        api.setRequestManager(new RequestManager(api));
    }

    public String getIdentifyPacket() {
        JSONObject identify = new JSONObject()
                .put("op", 2)
                .put("d", new JSONObject()
                        .put("token", getToken())
                        .put("properties", new JSONObject()
                                .put("$os", System.getProperty("os.name"))
                                .put("$browser", "discord.jar")
                                .put("$device", "discord.jar")
                                .put("$referring_domain", "")
                                .put("$referrer", "")
                        )
                        .put("v", WebSocketClient.DISCORD_GATEWAY_VERSION)
                        .put("large_threshold", 250)
                        .put("compress", true));    //Used to make the READY event be given as compressed binary data when over a certain size. TY @ShadowLordAlpha
        return identify.toString();
    }

    public Login() {
    }


    public String getToken() {
        return this.token;
    }


    public void setToken(final String token) {
        this.token = token;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Login)) return false;
        final Login other = (Login) o;
        final java.lang.Object this$token = this.getToken();
        final java.lang.Object other$token = other.getToken();
        if (this$token == null ? other$token != null : !this$token.equals(other$token)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof Login;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Login(token=" + this.getToken() + ")";
    }
}
