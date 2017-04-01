package discord.jar;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EmbedBuilder {
    private String title;
    private String type;
    private String description;
    private String url;
    private Color color;
    private Embed.EmbedFooter footer;
    private Embed.EmbedImage image;
    private Embed.EmbedImage thumbnail;
    private Embed.EmbedMedia video;
    private Embed.EmbedProvider provider;
    private Embed.EmbedAuthor author;
    private List<Embed.EmbedField> fields = new ArrayList<>();

    public EmbedBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public EmbedBuilder withType(String type) {
        this.type = type;
        return this;
    }

    public EmbedBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public EmbedBuilder withUrl(String url) {
        this.url = url;
        return this;
    }

    public EmbedBuilder withColor(Color color) {
        this.color = color;
        return this;
    }

    public EmbedBuilder withFooter(Embed.EmbedFooter footer) {
        this.footer = footer;
        return this;
    }

    public EmbedBuilder withFooter(String text, String iconUrl) {
        this.footer = new Embed.EmbedFooter(text, iconUrl, null);
        return this;
    }

    public EmbedBuilder withImage(Embed.EmbedImage image) {
        this.image = image;
        return this;
    }

    public EmbedBuilder withImage(String url) {
        this.image = new Embed.EmbedImage(url, null, -1, -1);
        return this;
    }

    public EmbedBuilder withThumbnail(Embed.EmbedImage thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public EmbedBuilder withThumbnail(String url) {
        this.thumbnail = new Embed.EmbedImage(url, null, -1, -1);
        return this;
    }

    public EmbedBuilder withVideo(Embed.EmbedMedia video) {
        this.video = video;
        return this;
    }

    public EmbedBuilder withVideo(String url) {
        this.video = new Embed.EmbedMedia(url, -1, -1);
        return this;
    }

    public EmbedBuilder withProvider(Embed.EmbedProvider provider) {
        this.provider = provider;
        return this;
    }

    public EmbedBuilder withProvider(String name, String url) {
        this.provider = new Embed.EmbedProvider(name, url);
        return this;
    }

    public EmbedBuilder withAuthor(Embed.EmbedAuthor author) {
        this.author = author;
        return this;
    }

    public EmbedBuilder withAuthor(String name, String url, String iconUrl) {
        this.author = new Embed.EmbedAuthor(name, url, iconUrl, null);
        return this;
    }

    public EmbedBuilder appendField(Embed.EmbedField field) {
        this.fields.add(field);
        return this;
    }

    public EmbedBuilder appendField(String name, String value, boolean inline) {
        this.fields.add(new Embed.EmbedField(name, value, inline));
        return this;
    }

    public Embed build() {
        return new Embed(title, type, description, url, color, footer, image, thumbnail, video, provider, author, fields.toArray(new Embed.EmbedField[0]));
    }
}
