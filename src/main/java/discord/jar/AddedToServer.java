package discord.jar;

public class AddedToServer {
    private final Server server;

    public Server getServer() {
        return this.server;
    }

    public AddedToServer(final Server server) {
        this.server = server;
    }
}
