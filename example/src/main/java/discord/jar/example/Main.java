package discord.jar.example;

import discord.jar.*;
import java.util.stream.Collectors;

public class Main implements EventListener {

	public Main() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException {

		DiscordAPI api = new DiscordBuilder("Mjk2NjczMTQwMTExODM1MTM2.C76oxA.R19q1bjQbYmN3jqIH4Hti1O0tvk").build();

		api.getEventManager().registerListener(this);

		api.login();

	}

	public static void main(String[] args) throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException {
		new Main();
	}

	public void on(LoadedEvent event) {
		System.out.println("Bot successfully loaded!\nIt's connected to the following servers: " + event.getApi().getAvailableServers().stream().map(s -> s.getName()).collect(Collectors.joining(", ")));
	}

	public void on(UserChatEvent event) {
		if (event.getMsg().getMessage().equalsIgnoreCase("hello bot")) {
			System.out.println("greeting user");
			event.getGroup().sendMessage("Hello user");
		}

	}

}
