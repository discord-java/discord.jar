package discord.jar;

import lombok.Getter;
import org.json.JSONObject;

public class RequestManager
{
    @Getter
    private WebSocketClient socketClient;

    public RequestManager(DiscordAPIImpl api) throws DiscordFailedToConnectException
    {
        try
        {
            PacketBuilder pb = new PacketBuilder(api);
            pb.setType(RequestType.GET);
            pb.setUrl("https://discordapp.com/api/gateway");
            String response = pb.makeRequest();

            if (response == null)
                throw new DiscordFailedToConnectException();

            socketClient = new WebSocketClient(api, new JSONObject(response).getString("url"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new DiscordFailedToConnectException();
        }
    }

}
