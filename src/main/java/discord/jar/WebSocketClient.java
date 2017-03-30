package discord.jar;

import org.java_websocket.client.DefaultSSLWebSocketClientFactory;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.ByteBuffer;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import javax.net.ssl.SSLContext;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    public static final int DISCORD_GATEWAY_VERSION = 6;
    
    public boolean loaded = false;
    protected Thread thread;
    private DiscordAPIImpl api;
    private String server;
    private Poll readyPoll;
    private Poll banPoll;
    private Poll addUserPoll;
    private Poll messagePoll;
    private Poll kickedPoll;
    private Poll typingPoll;
    private Poll newContactOrGroupPoll;
    private Poll statusPoll;
    private Poll updateSettings;
    private Poll channelRemovePoll;
    private Poll channelUpdatePoll;
    private Poll guildAddPoll;
    private Poll userUpdatePoll;
    private Poll deletePoll;

    public WebSocketClient(DiscordAPIImpl api, String url) {
        super(URI.create(url)); 
        if (url.startsWith("wss")) 
        { 
            try 
            { 
                SSLContext sslContext; 
                sslContext = SSLContext.getInstance("TLS"); 
                sslContext.init(null, null, null); 
                this.setWebSocketFactory(new DefaultSSLWebSocketClientFactory(sslContext)); 
            } 
            catch (NoSuchAlgorithmException | KeyManagementException e) 
            { 
                e.printStackTrace(); 
            } 
        } 
        this.api = api;
        readyPoll = new ReadyPoll(api);
        banPoll = new BanPoll(api);
        addUserPoll = new AddUserPoll(api);
        messagePoll = new MessagePoll(api);
        kickedPoll = new KickPoll(api);
        typingPoll = new TypingPoll(api);
        newContactOrGroupPoll = new NewContactOrGroupPoll(api);
        statusPoll = new StatusPoll(api);
        updateSettings = new UpdateSettings(api);
        channelRemovePoll = new ChannelRemove(api);
        channelUpdatePoll = new ChannelUpdatePoll(api);
        guildAddPoll = new GuildAdd(api);
        userUpdatePoll = new UserUpdatePoll(api);
        deletePoll = new DeleteMessagePoll(api);
        this.connect();
    }

    @Override
    public void onOpen(ServerHandshake handshakeData) {
        loaded = true;
        api.log("Identifying...");
        send(api.getLoginTokens().getIdentifyPacket());
        api.log("Logged in and loaded!");
        
    }

	@Override
	public void onMessage(ByteBuffer message) {
		try {

			//Thanks to ShadowLordAlpha for code and debugging.
			//Get the compressed message and inflate it
			StringBuilder builder = new StringBuilder();
			Inflater decompresser = new Inflater();
			byte[] bytes = message.array();
			decompresser.setInput(bytes, 0, bytes.length);
			byte[] result = new byte[128];
			while (!decompresser.finished()) {
				int resultLength = decompresser.inflate(result);
				builder.append(new String(result, 0, resultLength, "UTF-8"));
			}
			decompresser.end();

			// send the inflated message to the TextMessage method
			onMessage(builder.toString());
		} catch (DataFormatException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }
    
	@Override
	public void onClose(int code, String reason, boolean remote) {
		api.log("Disconnected... :(");
		if (code >= 4000) {
			api.log("Trying to reconnect");
			stop();
			api.clear();
			try {
				api.getLoginTokens().process(api);
			} catch (Exception e) {
				api.log("Failed to reconnect: " + e.getCause());
				api.stop();
			}
		} else {
			api.log("Schutting down api...");
			api.stop();
		}
	}

    @Override
    public void onMessage(String message) {
        try {
            JSONObject obj = new JSONObject(message);
            if (obj.getInt("op") == 7)
                return;
            JSONObject key = obj.getJSONObject("d");
            String type = obj.getString("t");

            Server a = key.isNull("guild_id") ? null : api.getServerById(key.getString("guild_id"));
            Server server = key.isNull("guild_id") ? null : (a != null ? a : api.getGroupById(key.getString
                    ("guild_id")).getServer());
            switch (type) {
                case "READY":
                    readyPoll.process(key, obj, server);
                    api.log("Successfully loaded user data!");
                    break;
                case "GUILD_MEMBER_ADD":
                    addUserPoll.process(key, obj, server);
                    break;
                case "GUILD_MEMBER_REMOVE":
                    kickedPoll.process(key, obj, server);
                    break;
                case "GUILD_BAN_ADD":
                    banPoll.process(key, obj, server);
                    break;
                case "GUILD_BAN_REMOVE":
                    //processBan(key, server);
                    //Unban?
                    break;
                case "MESSAGE_CREATE":
                    messagePoll.process(key, obj, server);
                    break;
                case "MESSAGE_UPDATE":
                    messagePoll.process(key, obj, server);
                    break;
                case "TYPING_START":
                    typingPoll.process(key, obj, server);
                    break;
                case "CHANNEL_CREATE":
                    newContactOrGroupPoll.process(key, obj, server);
                    break;
                case "PRESENCE_UPDATE":
                    statusPoll.process(key, obj, server);
                    break;
                case "USER_UPDATE":
                    updateSettings.process(key, obj, server);
                    break;
                case "CHANNEL_DELETE":
                    channelRemovePoll.process(key, obj, server);
                    break;
                case "CHANNEL_UPDATE":
                    channelUpdatePoll.process(key, obj, server);
                    break;
                case "GUILD_CREATE":
                    guildAddPoll.process(key, obj, server);
                    break;
                case "MESSAGE_DELETE":
                    userUpdatePoll.process(key, obj, server);
                    break;
                case "GUILD_MEMBER_UPDATE":
                    deletePoll.process(key, obj, server);
                    break;
                default:
                    api.log("Unknown type " + type + "\n >" + message);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("Internal client error!");
        api.log("Attempting go log in (again?)!");
        api.stop();
        try {
            api.login();
        } catch (Exception e) {
        }
    }

    public void stop() {
        this.close();
        ((ReadyPoll) readyPoll).stop();
    }

}


