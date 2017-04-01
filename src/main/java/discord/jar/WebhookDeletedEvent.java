package discord.jar;

public class WebhookDeletedEvent {
    private final Webhook webhook;

    public WebhookDeletedEvent(final Webhook webhook) {
        this.webhook = webhook;
    }
}
