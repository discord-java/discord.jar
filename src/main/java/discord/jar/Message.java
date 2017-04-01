package discord.jar;


public interface Message {
    
    /**
    * Gets the message
    *
    * @return The message as a string
    */
    String getMessage();

    /**
    * Sets the message to the specified String
    *
    * @param message The message to set it to
    */
    void setMessage(String message);

    /**
    * Gets the sender of the message
    *
    * @return The user who sent the message
    */
    User getSender();

    /**
    * Gets the ID of the message
    *
    * @return The ID
    */
    String getId();

    /**
    * Gets the ID of the group the message is in
    *
    * @return The ID
    */
    String getGroupId();

    /**
    * Checks to see if the message was edited or not
    *
    * @return True if it has been, false otherwise
    */
    boolean isEdited();

    /**
    * Applys a user tag to the message
    *
    * @param username The username to apply
    * @param server The group to apply
    */
    void applyUserTag(String username, Group server);

    /**
    * Deletes the message
    */
    void deleteMessage();

    /**
    * Edits the message with the specified String
    *
    * @param message The string to edit it with
    */
    void editMessage(String message);
}
