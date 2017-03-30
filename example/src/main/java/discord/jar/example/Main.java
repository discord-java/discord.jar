package discord.jar.example;

import discord.jar.*;
import java.util.stream.Collectors;

public class Main implements EventListener {

	public Main() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException {

		DiscordAPI api = new DiscordBuilder("TOKEN").build();

		api.getEventManager().registerListener(this);

		api.login();

	}

	public static void main(String[] args) throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException {
		new Main();
	}

	public void on(LoadedEvent event) {
		System.out.println("Bot successfully loaded!\n It's connected to the following servers: " + event.getApi().getAvailableServers().stream().map(s -> s.getName()).collect(Collectors.joining(", ")));
	}

	public void on(UserChatEvent event) {
		if (event.getMsg().getMessage().equalsIgnoreCase("hello bot")) {
			System.out.println("greeting user");
			event.getGroup().sendMessage("Hello user");
		}

	}

}
