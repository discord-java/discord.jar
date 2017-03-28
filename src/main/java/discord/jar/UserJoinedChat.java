package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserJoinedChat
{
    private final Server server;
    private final GroupUser groupUser;
}
