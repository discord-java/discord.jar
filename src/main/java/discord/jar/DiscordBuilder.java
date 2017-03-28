package discord.jar;

public class DiscordBuilder
{

    private String email;
    private String password;

    public DiscordBuilder(String email, String password)
    {
        this.email = email;
        this.password = password;
    }

    public DiscordBuilder()
    {
    }

    public DiscordAPI build()
    {
        return new DiscordAPIImpl(email, password);
    }
}
