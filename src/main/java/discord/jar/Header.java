package discord.jar;

public class Header {
    private final String type;
    private final String data;

    public String getType() {
        return this.type;
    }

    public String getData() {
        return this.data;
    }

    public Header(final String type, final String data) {
        this.type = type;
        this.data = data;
    }
}
