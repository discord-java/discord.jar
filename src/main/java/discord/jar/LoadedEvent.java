package discord.jar;

public class LoadedEvent {

	private DiscordAPI api;

	public LoadedEvent(DiscordAPI api) {
		this.api = api;
	}

	public DiscordAPI getApi() {
		return api;
	}

}
