package discord.jar;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;

public class PacketBuilder {
    protected DiscordAPIImpl api;
    //TODO: Recode -> this is from an older version of jSkype
    protected String data = "";
    protected String url = "";
    protected RequestType type = null;
    protected Boolean isForm = false;
    protected ArrayList<Header> headers = new ArrayList<Header>();
    protected HttpURLConnection con;
    protected boolean sendLoginHeaders = true;
    protected boolean file = false;
    protected int code = 200;
    protected String cookies = "";

    public PacketBuilder(DiscordAPIImpl api) {
        this.api = api;
    }

    @Deprecated
    protected void addLogin(DiscordAPIImpl skype) {
        addHeader(new Header("authorization", skype.getLoginTokens().getToken()));
    }

    public void addHeader(Header header) {
        headers.add(header);
    }

    public String makeRequest() {
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(type.toString().equals("PATCH") ? "POST" : type.toString());
            if (type == RequestType.PATCH) {
                try {
                    // apaches api isn't working very well for me
                    // and java doesn't support patch... so why
                    // not completely recode it for what we need?
                    Socket clientSocket = new Socket("discordapp.com", 80);
                    DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());
                    out.writeBytes("PATCH " + url + " HTTP/1.1\nHost: discordapp.com\nConnection: keep-alive\nContent-Length: " + data.length() + "\nOrigin: http://discordapp.com\nUser-Agent: Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36 OPR/31.0.1889.151\nContent-Type: application/json\nAccept: */*\nauthorization: " + api.getLoginTokens().getToken() + "\n\n" + data);
                    out.close();
                    clientSocket.close();
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    api.log("================================================");
                    api.log("=========Unable to request discord.com =========");
                    api.log("================================================");
                    api.log("Error: " + e.getMessage());
                    api.log("URL: " + url);
                    return null;
                }
            }
            con.setRequestProperty("Content-Type", isForm ? "application/x-www-form-urlencoded" : (file ? "application/octet-stream" : "application/json; charset=utf-8"));
            con.setRequestProperty("Content-Length", Integer.toString(data.getBytes().length));
            con.setRequestProperty("User-Agent", "0/7.7.0.103// libhttpX.X");
            if (!cookies.equals("")) con.setRequestProperty("Cookie", cookies);
            con.setDoOutput(true);
            if (sendLoginHeaders) addLogin(api);
            for (Header s : headers) con.addRequestProperty(s.getType(), s.getData());
            if (!(data.getBytes().length == 0)) {
                DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                wr.write(data.getBytes());
                wr.flush();
                wr.close();
            }
            code = con.getResponseCode();
            if (code == 200 || code == 201 || code == 404) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else if (code == 204) {
                return "";
            } else {
                //GetProfile will handle the debugging info
                if (url.equals("https://api.skype.com/users/self/contacts/profiles")) return null;
                //Debug info
                if (api.isDebugMode()) {
                    for (Header header : headers) api.log(header.getType() + ": " + header.getData());
                    api.log("Error contacting skype\nUrl: " + url + "\nCode: " + code + "\nData: " + data + "\nType: " + type);
                }
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public String getData() {
        return this.data;
    }

    public void setData(final String data) {
        this.data = data;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public RequestType getType() {
        return this.type;
    }

    public void setType(final RequestType type) {
        this.type = type;
    }

    public Boolean getIsForm() {
        return this.isForm;
    }

    public void setIsForm(final Boolean isForm) {
        this.isForm = isForm;
    }

    public ArrayList<Header> getHeaders() {
        return this.headers;
    }

    public HttpURLConnection getCon() {
        return this.con;
    }

    public boolean isSendLoginHeaders() {
        return this.sendLoginHeaders;
    }

    public void setSendLoginHeaders(final boolean sendLoginHeaders) {
        this.sendLoginHeaders = sendLoginHeaders;
    }

    public boolean isFile() {
        return this.file;
    }

    public void setFile(final boolean file) {
        this.file = file;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getCookies() {
        return this.cookies;
    }

    public void setCookies(final String cookies) {
        this.cookies = cookies;
    }
}
