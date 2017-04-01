package discord.jar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.util.ArrayList;

public class Embed {
    private String title;
    private String type;
    private String description;
    private String url;
    private Color color;
    private EmbedFooter footer;
    private EmbedImage image;
    private EmbedImage thumbnail;
    private EmbedMedia video;
    private EmbedProvider provider;
    private EmbedAuthor author;
    private EmbedField[] fields;

    public Embed(String title, String type, String description, String url, Color color, EmbedFooter footer, EmbedImage image, EmbedImage thumbnail, EmbedMedia video, EmbedProvider provider, EmbedAuthor author, EmbedField[] fields) {
        this.title = title;
        this.type = type;
        this.description = description;
        this.url = url;
        this.color = color;
        this.footer = footer;
        this.image = image;
        this.thumbnail = thumbnail;
        this.video = video;
        this.provider = provider;
        this.author = author;
        this.fields = fields;
    }

    public Embed(JSONObject json) {
        if(json.has("title"))
            title = json.getString("title");

        if(json.has("type"))
            type = json.getString("type");

        if(json.has("description"))
            description = json.getString("description");

        if(json.has("url"))
            url = json.getString("url");

        if(json.has("color"))
            color = new Color(json.getInt("color"));

        if(json.has("footer"))
            footer = new EmbedFooter(json.getJSONObject("footer"));

        if(json.has("image"))
            image = new EmbedImage(json.getJSONObject("image"));

        if(json.has("thumbnail"))
            thumbnail = new EmbedImage(json.getJSONObject("thumbnail"));

        if(json.has("video"))
            video = new EmbedMedia(json.getJSONObject("video"));

        if(json.has("provider"))
            provider = new EmbedProvider(json.getJSONObject("provider"));

        if(json.has("author"))
            author = new EmbedAuthor(json.getJSONObject("author"));

        if(json.has("fields")) {
            ArrayList<EmbedField> fields = new ArrayList<>();
            for (Object obj : json.getJSONArray("fields"))
                fields.add(new EmbedField((JSONObject) obj));
        }
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public Color getColor() {
        return color;
    }

    public EmbedFooter getFooter() {
        return footer;
    }

    public EmbedImage getImage() {
        return image;
    }

    public EmbedImage getThumbnail() {
        return thumbnail;
    }

    public EmbedMedia getVideo() {
        return video;
    }

    public EmbedProvider getProvider() {
        return provider;
    }

    public EmbedAuthor getAuthor() {
        return author;
    }

    public EmbedField[] getFields() {
        return fields;
    }

    public JSONObject toJson() {
        JSONArray fieldArray = new JSONArray();
        for (EmbedField field : fields) fieldArray.put(field.toJson());
        return new JSONObject()
                .put("title", title).put("type", type).put("description", description).put("url", url)
                .put("color", color == null ? null : ((color.getRed() & 0xFF) << 16) | ((color.getGreen() & 0xFF) << 8) | (color.getBlue() & 0xFF))
                .put("footer", footer == null ? null : footer.toJson()).put("image", image == null ? null : image.toJson()).put("thumbnail", thumbnail == null ? null : thumbnail.toJson())
                .put("video", video == null ? null : video.toJson()).put("provider", provider == null ? null : provider.toJson()).put("author", author == null ? null : author.toJson()).put("fields", fieldArray);

    }

    static class EmbedFooter {
        private String text;
        private String iconUrl;
        private String proxiedUrl;

        EmbedFooter(String text, String iconUrl, String proxiedUrl) {
            this.text = text;
            this.iconUrl = iconUrl;
            this.proxiedUrl = proxiedUrl;
        }

        public EmbedFooter(JSONObject footer) {
            if(footer.has("text"))
                this.text = footer.getString("text");

            if(footer.has("icon_url"))
                this.iconUrl = footer.getString("icon_url");

            if(footer.has("proxied_url"))
                this.proxiedUrl = footer.getString("proxied_url");
        }

        public String getText() {
            return text;
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public String getProxiedUrl() {
            return proxiedUrl;
        }

        public JSONObject toJson() {
            return new JSONObject().put("text", text).put("iconUrl", iconUrl).put("proxiedUrl", proxiedUrl);
        }
    }

    static class EmbedMedia {
        private String url;
        private int width;
        private int height;

        EmbedMedia(String url, int width, int height) {
            this.url = url;
            this.width = width;
            this.height = height;
        }

        EmbedMedia(JSONObject media) {
            if(media.has("url"))
                this.url = media.getString("url");

            if(media.has("width"))
                this.width = media.getInt("width");

            if(media.has("height"))
                this.height = media.getInt("height");
        }

        public String getUrl() {
            return url;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public JSONObject toJson() {
            return new JSONObject().put("url", url).put("width", width).put("height", height);
        }
    }

    static class EmbedImage extends EmbedMedia {
        private String proxiedUrl;

        EmbedImage(String url, String proxiedUrl, int width, int height) {
            super(url, width, height);
            this.proxiedUrl = proxiedUrl;
        }

        public EmbedImage(JSONObject image) {
            super(image);

            if(image.has("proxied_url"))
                proxiedUrl = image.getString("proxied_url");
        }

        public String getProxiedUrl() {
            return proxiedUrl;
        }

        @Override
        public JSONObject toJson() {
            return super.toJson().put("proxied_url", proxiedUrl);
        }
    }

    static class EmbedProvider {
        private String name;
        private String url;

        EmbedProvider(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public EmbedProvider(JSONObject provider) {
            if(provider.has("name"))
                this.name = provider.getString("name");

            if(provider.has("url"))
                this.url = provider.getString("url");
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public JSONObject toJson() {
            return new JSONObject().put("name", name).put("url", url);
        }
    }

    static class EmbedAuthor extends EmbedProvider {
        private String iconUrl;
        private String proxiedUrl;

        EmbedAuthor(String name, String url, String iconUrl, String proxiedUrl) {
            super(name, url);
            this.iconUrl = iconUrl;
            this.proxiedUrl = proxiedUrl;
        }

        public EmbedAuthor(JSONObject author) {
            super(author);

            if(author.has("icon_url"))
                this.iconUrl = author.getString("icon_url");

            if(author.has("proxied_url"))
                this.proxiedUrl = author.getString("proxied_url");
        }

        public String getIconUrl() {
            return iconUrl;
        }

        public String getProxiedUrl() {
            return proxiedUrl;
        }

        @Override
        public JSONObject toJson() {
            return super.toJson().put("icon_url", iconUrl).put("proxied_url", proxiedUrl);
        }
    }

    static class EmbedField {
        private String name;
        private String value;
        private boolean inline;

        EmbedField(String name, String value, boolean inline) {
            this.name = name;
            this.value = value;
            this.inline = inline;
        }

        public EmbedField(JSONObject field) {
            if(field.has("name"))
                this.name = field.getString("name");

            if(field.has("value"))
                this.value = field.getString("value");

            if(field.has("inline"))
                this.inline = field.getBoolean("inline");
        }

        public String getName() {
            return name;
        }

        public String getValue() {
            return value;
        }

        public boolean isInline() {
            return inline;
        }

        public JSONObject toJson() {
            return new JSONObject().put("name", name).put("value", value).put("inline", inline);
        }
    }
}

