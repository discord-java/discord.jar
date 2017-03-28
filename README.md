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



# Creating a DiscordAPI instance

In order to create the DiscordAPI instance, you'll need to use the DiscordBuilder class. 

Examples:
```java
DiscordAPI api = new DiscordBuilder("email", "pass").build().login();

DiscordAPI api = new DiscordBuilder("email", "pass").build();
api.login();
```

# Using the event manager
In order to listen for an event, create a class that implements EventListener, and register it by calling `api.getEventManager().registerListener(new YourListener(api));`. All events can be found in the `me.itsghost.jdiscord.events` package as well as the [Events](#events) section. 

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
### (Now using shaded jar due to compatibility issues with past builds)
[Maven](http://itsghost.me/maven)

Repository:
```
<repository>
  <id>xyz.gghost</id>
  <url>http://gghost.xyz/maven/</url>
</repository>
```
Dependency:
```
<dependency>
  <groupId>xyz.gghost</groupId>
  <artifactId>jdiscord</artifactId>
  <version>1.3</version>
  <scope>compile</scope>
</dependency>
```


# Dependencies
- [Apache Commons Lang 3](https://commons.apache.org/proper/commons-lang/)
- [lombok](https://projectlombok.org/)
- [JSON](http://www.json.org/java/)
- [Java-Websocket](https://github.com/tootallnate/java-websocket)
- http://itsghost.me/commons-codec-1.10.jar
