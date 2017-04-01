package discord.jar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WebhookUpdatePoll implements Poll {
    private DiscordAPIImpl api;

    public WebhookUpdatePoll(DiscordAPIImpl api) {
        this.api = api;
    }

    @Override
    public void process(JSONObject content, JSONObject rawRequest, Server server) {
        System.out.println(content);
        System.out.println(rawRequest);

        Group group = server.getGroupById(content.getString("channel_id"));
        if (group != null) {
            PacketBuilder pb = new PacketBuilder(api);
            pb.setType(RequestType.GET);
            pb.setUrl("https://discordapp.com/api/channels/" + group.getId() + "/webhooks");
            String a = pb.makeRequest();
            if (a != null && pb.getCode() == 200) {
                if (!a.contains("message")) {
                    JSONArray webhooks = new JSONArray(a);
                    List<String> presentWebhooks = new ArrayList<>(webhooks.length());
                    for (Object obj : webhooks) {
                        if (obj instanceof JSONObject) {
                            JSONObject webhook = (JSONObject) obj;
                            String webhookId = webhook.getString("id");
                            if(server.getWebhookById(webhookId) == null) {
                                WebhookImpl webhookImpl = new WebhookImpl(webhook.getString("name"), webhook.isNull("avatar") ? null : webhook.getString("avatar"), webhook.getString("token"), webhook.getString("id"), webhook.getString("channel_id"), api);
                                server.getWebhooks().add(webhookImpl);
                                api.getEventManager().executeEvent(new WebhookCreatedEvent(webhookImpl));
                            }
                            else {
                                WebhookImpl webhookImpl = (WebhookImpl) server.getWebhookById(webhookId);
                                String newName = webhook.getString("name");
                                String newAvatar = webhook.isNull("avatar") ? null : webhook.getString("avatar");
                                String newGroupId = webhook.getString("channel_id");
                                if(!Objects.equals(newName, webhookImpl.getName()) || !Objects.equals(newAvatar, webhookImpl.getAvatar()) || !(Objects.equals(newGroupId, webhookImpl.getGroupId()))) {
                                    webhookImpl.setName(newName);
                                    webhookImpl.setAvatar(newAvatar);
                                    webhookImpl.setGroupId(newGroupId);
                                    api.getEventManager().executeEvent(new WebhookUpdatedEvent(webhookImpl));
                                }
                            }

                            presentWebhooks.add(webhookId);
                        }
                    }

                    for (int i = 0; i < group.getWebhooks().size(); i++) {
                        Webhook webhook = group.getWebhooks().get(i);
                        if (!presentWebhooks.contains(webhook.getId())) {
                            server.getWebhooks().remove(webhook);
                            api.getEventManager().executeEvent(new WebhookDeletedEvent((webhook)));
                        }
                    }
                }
            }
        }
    }
}
