package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserTypingEvent
{
    private final Group group;
    private final GroupUser user;

    public Server getServer()
    {
        return group.getServer();
    }
}
