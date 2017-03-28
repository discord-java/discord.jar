package discord.jar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Header
{
    private final String type;
    private final String data;
}
