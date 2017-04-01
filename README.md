# April Fools!
Thanks for playing! Love, JDA and D4J devs.

<img align="right" src="https://images.discordapp.net/.eJwdyUEOhCAMAMC_8ACqAgv1Nw0SdKOW0Hoy_t3NXmduc_XdzGZVbTIDLJtk7osV5U612Mpc90JtE5v5AFKlvB7lVIEJAwY_-JCSHxyOMf0phunjUnQRf4fwpW7bWc3zAn-XIS8.GAnKML6JteYdVkafLF4yONWL8xU" height="256" width="256">

# discord.jar

The single best Discord API java wrapper

[Download](#shut-up-and-take-my-money) with maven

# Features
- Kicking and banning                   (API exclusive)
- Profile settings/account settings		(API exclusive)
- Message building
- Online statuses						(This includes your own status! API exclusive)
- Avatars + Roles 
- DMs
- Group messaging
- User talk (edited) event 
- User join/banned/kicked events
- Invite joining
- Much more... 

### TODO
- Message history         (nearly done)
- Game status
- VOIP (Java and C# Host) (currently experimental)

# Events
- AddedToServer       (AddedToGuildEvent)
- APILoadedEvent      (You might get NPEs if you don't wait for this)
- ChannelCreatedEvent (group/channel)
- ChannelDeletedEvent (group/channel)
- ChannelUpdatedEvent (group/channel)
- UserBannedEvent
- UserChatEvent
- UserJoinedEvent
- UserKickedEvent
- UserTypingEvent
- UserOnlineStatusChangedEvent
- UserDeletedMessageEvent
- MentionEvent (1.3)
- WebhookCreatedEvent
- WebhookDeletedEvent
- WebhookUpdatedEvent



# Creating a DiscordAPI instance

In order to create the DiscordAPI instance, you'll need to use the DiscordBuilder class. 

Examples:
```java
DiscordAPI api = new DiscordBuilder("email", "pass").build().login();

DiscordAPI api = new DiscordBuilder("email", "pass").build();
api.login();
```

# Using the event manager
In order to listen for an event, create a class that implements EventListener, and register it by calling `api.getEventManager().registerListener(new YourListener(api));`. All events can be found in the `discord.jar` package as well as the [Events](#events) section. 

```java
public class ExampleListener implements EventListener {
    DiscordAPI api;
    public ExampleListener(DiscordAPI api){
        this.api = api;
    }
    public void userChat(UserChatEvent e){
        if (e.getMsg().getMessage().equals("#ping")){
            e.getGroup().sendMessage(new MessageBuilder()
                    .addString("Yes, ")
                    .addUserTag(e.getGroupUser(), e.getGroup())
                    .addString("?")
                    .build());
        }
        System.out.println((e.getMsg().isEdited() ? "# " : "") + "[" + e.getGroup().getName() + "] " + e.getGroupUser() + " > " + e.getMsg().getMessage());
    }
    public void typing(UserTypingEvent e){
        System.out.println(e.getGroupUser() + " is typing in " + e.getGroup());
    }
}

public class Test {
    public static void main(String[] args) {
        DiscordAPI api = new DiscordAPI("email", "pass").login();
        api.getEventManager().registerListener(new ExampleListener(api)); //Register listener
    }
}
```
# Shut up and take my money! 
```groovy
repositories {
  jcenter()
  maven {
    url  "https://jitpack.io"
  }
}

dependencies {
  compile "com.github.discord-java:discord.jar:master-VERSION"
}
```
# Dependencies
- [Apache Commons](https://commons.apache.org/)
- [JSON](http://www.json.org/java/)
- [Java-Websocket](https://github.com/tootallnate/java-websocket)
