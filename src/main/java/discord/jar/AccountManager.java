package discord.jar;

import java.io.IOException;
import java.io.InputStream;

public interface AccountManager {
    /**
    * Sets the Online Status of the account
    *
    * @param online Whether they are online or not
    */
    void setOnlineStatus(boolean online);

    /**
    * Sets the display name of the account
    *
    * @param displayName The new Display name
    */
    void setDisplayName(String displayName);

    /**
    * Sets the avatar of the account
    *
    * @param is The input stream 
    * @throws IOException
    */
    void setAvatar(InputStream is) throws IOException;

    /**
    * Sets the account's password
    *
    * @param pass The password to change it to
    */
    void changePass(String pass);

    /**
    * Sets the account's email
    *
    * @param pass The email to change it to
    */
    void changeEmail(String pass);
}
