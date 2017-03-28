package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserBannedEvent
{
    private final Server server;
    private final GroupUser groupUser;
}
