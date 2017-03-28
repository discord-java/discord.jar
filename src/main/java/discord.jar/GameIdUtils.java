package discord.jar;


import org.json.JSONArray;
import org.json.JSONObject;

public class GameIdUtils
{

    private static String GAMEARRAY;

    public static String getGameFromId(int id)
    {
        JSONArray array = new JSONArray(getGameArray());
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject item = array.getJSONObject(i);

            if (item.getInt("id") == id)
                return item.getString("name");
        }
        return "Unknown game";
    }

    private static String getGameArray()
    {
        if (GAMEARRAY != null)
            return GAMEARRAY;

        PacketBuilder pb = new PacketBuilder(null);
        pb.setSendLoginHeaders(false);
        pb.setType(RequestType.GET);
        pb.setUrl("http://pastebin.com/raw.php?i=e94CkJpk");
        GAMEARRAY = pb.makeRequest();

        return GAMEARRAY;
    }
}
