package discord.jar;

public class GroupUser {
    private User user;
    private String role;
    private String discriminator;

    public GroupUser(User user, String role, String discriminator) {
        this.user = user;
        this.role = role;
        this.discriminator = discriminator;
    }

    public String toString() {
        return user.getUsername();
    }

    public User getUser() {
        return this.user;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String getDiscriminator() {
        return this.discriminator;
    }
}
