package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserDeletedMessageEvent
{
    private final Group group;
    private final String id;

    public Server getServer()
    {
        return group.getServer();
    }
}
