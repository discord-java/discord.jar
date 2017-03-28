package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserKickedEvent
{
    private final Server server;
    private final GroupUser groupUser;
}
