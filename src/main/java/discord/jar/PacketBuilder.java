package discord.jar;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import java.util.ArrayList;

public class PacketBuilder {
    protected DiscordAPIImpl api;
    //TODO: Recode -> this is from an older version of jSkype
    protected String data = "";
    protected String url = "";
    protected RequestType type = null;
    protected Boolean isForm = false;
    protected ArrayList<Header> headers = new ArrayList<Header>();
    protected boolean sendLoginHeaders = true;
    protected boolean file = false;
    protected int code = 200;

    public PacketBuilder(DiscordAPIImpl api) {
        this.api = api;
    }

    public String makeRequest() {
    	try {
    		HttpRequestWithBody request;
        	
        	switch (type) {
    			case DELETE:
    				request = Unirest.delete(url);
    				break;
    			case OPTIONS:
    				request = Unirest.options(url);
    				break;
    			case PATCH:
    				request = Unirest.patch(url);
    				break;
    			case POST:
    				request = Unirest.post(url);
    				break;
    			case PUT:
    				request = Unirest.put(url);
    				break;
    			case GET:
    				return Unirest.get(url).header("authorization", "Bot " + api.getLoginTokens().getToken()).header("Content-Type", isForm ? "application/x-www-form-urlencoded" : (file ? "application/octet-stream" : "application/json; charset=utf-8")).asString().getBody();
    			default:
    				throw new RuntimeException();
    		}
        	return request.header("authorization", "Bot " + api.getLoginTokens().getToken()).header("Content-Type", isForm ? "application/x-www-form-urlencoded" : (file ? "application/octet-stream" : "application/json; charset=utf-8")).body(data).asString().getBody();
		} catch (UnirestException e) {
			throw new RuntimeException(e);
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
}
