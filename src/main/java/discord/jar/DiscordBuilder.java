package discord.jar;

public class DiscordBuilder {

    private String token;

    public DiscordBuilder(String token) {
        this.token = token;
    }

    public DiscordBuilder() {
    }

    public void setToken(String token) {
		this.token = token;
	}

	public DiscordAPI build() {
        return new DiscordAPIImpl(token);
    }
}
