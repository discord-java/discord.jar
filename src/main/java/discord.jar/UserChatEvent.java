package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserChatEvent
{
    private final Group group;
    private final GroupUser user;
    private Message msg;

    public Server getServer()
    {
        return group.getServer();
    }
}
