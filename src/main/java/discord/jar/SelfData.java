package discord.jar;

public class SelfData {
    private String username;
    private String email;
    private String id;
    private String avatar;
    private String avatarId;

    public SelfData() {
    }

    public String getUsername() {
        return this.username;
    }

    public String getEmail() {
        return this.email;
    }

    public String getId() {
        return this.id;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public String getAvatarId() {
        return this.avatarId;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setAvatar(final String avatar) {
        this.avatar = avatar;
    }

    public void setAvatarId(final String avatarId) {
        this.avatarId = avatarId;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object o) {
        if (o == this) return true;
        if (!(o instanceof SelfData)) return false;
        final SelfData other = (SelfData) o;
        if (!other.canEqual((java.lang.Object) this)) return false;
        final java.lang.Object this$username = this.getUsername();
        final java.lang.Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final java.lang.Object this$email = this.getEmail();
        final java.lang.Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        final java.lang.Object this$id = this.getId();
        final java.lang.Object other$id = other.getId();
        if (this$id == null ? other$id != null : !this$id.equals(other$id)) return false;
        final java.lang.Object this$avatar = this.getAvatar();
        final java.lang.Object other$avatar = other.getAvatar();
        if (this$avatar == null ? other$avatar != null : !this$avatar.equals(other$avatar)) return false;
        final java.lang.Object this$avatarId = this.getAvatarId();
        final java.lang.Object other$avatarId = other.getAvatarId();
        if (this$avatarId == null ? other$avatarId != null : !this$avatarId.equals(other$avatarId)) return false;
        return true;
    }

    protected boolean canEqual(final java.lang.Object other) {
        return other instanceof SelfData;
    }

    @java.lang.Override
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final java.lang.Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final java.lang.Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final java.lang.Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final java.lang.Object $avatar = this.getAvatar();
        result = result * PRIME + ($avatar == null ? 43 : $avatar.hashCode());
        final java.lang.Object $avatarId = this.getAvatarId();
        result = result * PRIME + ($avatarId == null ? 43 : $avatarId.hashCode());
        return result;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "SelfData(username=" + this.getUsername() + ", email=" + this.getEmail() + ", id=" + this.getId() + ", avatar=" + this.getAvatar() + ", avatarId=" + this.getAvatarId() + ")";
    }
}
