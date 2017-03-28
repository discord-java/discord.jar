package discord.jar;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class PacketBuilder
{
    protected DiscordAPIImpl api;
    //TODO: Recode -> this is from an older version of jSkype
    @Getter
    @Setter
    protected String data = "";
    @Getter
    @Setter
    protected String url = "";
    @Getter
    @Setter
    protected RequestType type = null;
    @Getter
    @Setter
    protected Boolean isForm = false;
    @Getter
    protected ArrayList<Header> headers = new ArrayList<Header>();
    @Getter
    protected HttpURLConnection con;
    @Getter
    @Setter
    protected boolean sendLoginHeaders = true;
    @Getter
    @Setter
    protected boolean file = false;
    @Getter
    @Setter
    protected int code = 200;
    @Getter
    @Setter
    protected String cookies = "";

    public PacketBuilder(DiscordAPIImpl api)
    {
        this.api = api;
    }

    @Deprecated
    protected void addLogin(DiscordAPIImpl skype)
    {
        addHeader(new Header("authorization", skype.getLoginTokens().getToken()));
    }

    public void addHeader(Header header)
    {
        headers.add(header);
    }

    public String makeRequest()
    {
        try
        {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod(type.toString().equals("PATCH") ? "POST" : type.toString());

            if (type == RequestType.PATCH)
            {
                try
                {
                    // apaches api isn't working very well for me
                    // and java doesn't support patch... so why
                    // not completely recode it for what we need?
                    Socket clientSocket = new Socket("discordapp.com", 80);
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                    out.writeBytes("PATCH " + url + " HTTP/1.1\n" +
                            "Host: discordapp.com\n" +
                            "Connection: keep-alive\n" +
                            "Content-Length: " + data.length() + "\n" +
                            "Origin: http://discordapp.com\n" +
                            "User-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) " +
                            "Chrome/44.0.2403.130 Safari/537.36 OPR/31.0.1889.151\n" +
                            "Content-Type: application/json\n" +
                            "Accept: */*\n" +
                            "authorization: " + api.getLoginTokens().getToken() + "\n\n" + data);
                    out.close();
                    clientSocket.close();
                    return null;

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    api.log("================================================");
                    api.log("=========Unable to request discord.com =========");
                    api.log("================================================");
                    api.log("Error: " + e.getMessage());
                    api.log("URL: " + url);
                    return null;
                }
            }

            con.setRequestProperty("Content-Type", isForm ? "application/x-www-form-urlencoded" : (file ?
                    "application/octet-stream" : "application/json; charset=utf-8"));
            con.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
            con.setRequestProperty("User-Agent", "0/7.7.0.103// libhttpX.X");
            if (!cookies.equals(""))
                con.setRequestProperty("Cookie", cookies);
            con.setDoOutput(true);
            if (sendLoginHeaders)
                addLogin(api);
            for (Header s : headers)
                con.addRequestProperty(s.getType(), s.getData());

            if (!(data.getBytes().length == 0))
            {
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.write(data.getBytes());
                wr.flush();
                wr.close();
            }
            code = con.getResponseCode();
            if (code == 200 || code == 201 || code == 404)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null)
                {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
            else if (code == 204)
            {
                return "";
            }
            else
            {
                //GetProfile will handle the debugging info
                if (url.equals("https://api.skype.com/users/self/contacts/profiles"))
                    return null;

                //Debug info
                if (api.isDebugMode())
                {
                    for (Header header : headers)
                        api.log(header.getType() + ": " + header.getData());
                    api.log("Error contacting skype\nUrl: " + url + "\nCode: " + code + "\nData: " + data + "\nType: " + type);
                }
                return null;
            }

        }
        catch (Exception e)
        {
            return null;
        }
    }
}
