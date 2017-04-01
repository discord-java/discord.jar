package discord.jar;

public interface User {
    /**
    * Gets the Group the user is in.
    *
    * @return The group
    */
    Group getGroup();

    /**
    * Gets the URL to the user's avatar.
    *
    * @return The avatar URL
    */
    String getAvatar();
    
    /**
    * Gets the username of the user
    *
    * @return The username
    */
    String getUsername();

    /**
    * Gets the ID of the user
    *
    * @return The ID
    */
    String getId();

    /**
    * Gets the game the user is playing
    *
    * @return The game
    */
    String getGame();
    
    /**
    * Gets the formatted mention of a user
    *
    * @return The formatted string
    */
    String mention();

    /**
    * Gets the status of the user
    *
    * @return The OnlineStatus
    */
    OnlineStatus getOnlineStatus();
}
