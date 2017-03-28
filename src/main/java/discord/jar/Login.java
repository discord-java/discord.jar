package discord.jar;

import org.json.JSONObject;

public class Login {
    private String username;
    private String password;
    private String token;

    public void process(DiscordAPIImpl api) throws BadUsernamePasswordException, DiscordFailedToConnectException {
        api.log("Attempting to login!");
        JSONObject outJson = getTokens(api);
        if (outJson.isNull("token")) throw new BadUsernamePasswordException();
        token = outJson.getString("token");
        api.log("Logged in and starting session!");
        api.log("Token: " + token);
        api.setRequestManager(new RequestManager(api));
        WebSocketClient socket = api.getRequestManager().getSocketClient();
        while (!socket.loaded) {
            try {
                Thread.sleep(200);
            } catch (Exception e) {
            }
        }
        socket.send(getPCMetadata());
    }

    public JSONObject getTokens(DiscordAPIImpl api) throws DiscordFailedToConnectException {
        PacketBuilder pb = new PacketBuilder(api);
        pb.setSendLoginHeaders(false);
        pb.setData(new JSONObject().put("email", username).put("password", password).toString());
        pb.setUrl("https://discordapp.com/api/auth/login");
        pb.setType(RequestType.POST);
        final String out = pb.makeRequest();
        if (out == null) throw new DiscordFailedToConnectException();
        return new JSONObject(out);
    }

    public String getPCMetadata() {
        JSONObject login = new JSONObject();
        login.put("op", 2).put("d", new JSONObject().put("token", token).put("properties", new JSONObject().put("$os", "Windows").put("$browser", "jDiscord by GitHub user NotGGhost").put("$device", "").put("$referring_domain", "t.co").put("$referrer", "")).put("v", 3));
        return login.toString();
    }

    public Login() {
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getToken() {
        return this.token;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof Login)) return false;
        final Login other = (Login) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$username = this.getUsername();
        final java.lang.Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final java.lang.Object this$password = this.getPassword();
        final java.lang.Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
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
        final java.lang.Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final java.lang.Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        final java.lang.Object $token = this.getToken();
        result = result * PRIME + ($token == null ? 43 : $token.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Login(username=" + this.getUsername() + ", password=" + this.getPassword() + ", token=" + this.getToken() + ")";
    }
}
