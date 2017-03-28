package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ChannelCreatedEvent
{
    private final Group group;
}
