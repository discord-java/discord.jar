package discord.jar;

public class WebhookUpdatedEvent {
    private final Webhook webhook;

    public WebhookUpdatedEvent(final Webhook webhook) {
        this.webhook = webhook;
    }
}
