package discord.jar;

public class WebhookCreatedEvent {
    private final Webhook webhook;

    public WebhookCreatedEvent(final Webhook webhook) {
        this.webhook = webhook;
    }
}
