package discord.jar;


import org.json.JSONObject;

public interface Poll {
    void process(JSONObject content, JSONObject rawRequest, Server server);
}
