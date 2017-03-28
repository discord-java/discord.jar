package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class UserOnlineStatusChangedEvent
{
    private final User user;
    private final OnlineStatus status;
    private final String game;
}
