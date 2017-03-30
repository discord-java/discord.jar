package discord.jar;

import java.util.*;

public class DiscordAPIImpl implements DiscordAPI {
    private Login loginTokens = new Login();
    private List<User> availableDms = new ArrayList<User>();
    private List<Server> availableServers = new ArrayList<Server>();
    private Map<String, Group> userGroups = new HashMap<String, Group>();
    private boolean debugMode = false;
    private boolean allowLogMessages = true;
    private EventManager eventManager = new EventManager();
    private RequestManager requestManager;
    private SelfData selfInfo;
    private boolean loaded = false;
    private String as = "";
    private AccountManager accountManager = new AccountManagerImpl(this);
    private final Long startedTime = System.currentTimeMillis();

    private final List<String> unavailableServers = Collections.synchronizedList(new ArrayList<>());
	private boolean sendReady;

	public void sendReady() {
		this.sendReady = true;
	}

	public boolean hasSentReady() {
		return sendReady;
	}

	public List<String> getUnavailableServers() {
		return unavailableServers;
	}

	public DiscordAPIImpl(String token) {
        loginTokens.setToken(token);
    }

    public DiscordAPIImpl() {
    }

    public DiscordAPIImpl login(String token) throws BadUsernamePasswordException, DiscordFailedToConnectException {
        loginTokens.setToken(token);
        try {
            login();
        } catch (NoLoginDetailsException e) {
        } catch (BadUsernamePasswordException | DiscordFailedToConnectException e) {
            throw e;
        }
        return this;
    }

    public DiscordAPIImpl login() throws NoLoginDetailsException, BadUsernamePasswordException, DiscordFailedToConnectException {
        if (loginTokens.getToken() == null)
            throw new NoLoginDetailsException();
        loginTokens.process(this);
        return this;
    }

    public void log(String log) {
        if (allowLogMessages) System.out.println("DiscordAPI: " + log);
    }

    public Group getGroupById(String id) {
        for (User channel : availableDms) if (channel.getId().equals(id)) return channel.getGroup();
        for (Server server : availableServers)
            for (Group channel : server.getGroups()) if (channel.getId().equals(id)) return channel;
        return null;
    }

    public Server getServerById(String id) {
        for (Server server : availableServers) if (server.getId().equals(id)) return server;
        return null;
    }

    public User getUserByUsername(String id) {
        for (User user : availableDms) if (user.getUsername().equals(id)) return user;
        return null;
    }

    public User getUserById(String id) {
        for (User user : availableDms) if (user.equals(id)) return user;
        return null;
    }

    public boolean isUserKnown(String id) {
        return getUserById(id) != null;
    }

    public void stop() {
        log("Shutting down!");
        requestManager.getSocketClient().stop();
    }

    public void updateContact(User user) {
        ArrayList<User> users = new ArrayList<User>();
        for (User userA : availableDms) if (userA.getId().equals(user.getId())) users.add(userA);
        availableDms.removeAll(users);
        availableDms.add(user);
    }

    public Boolean joinInviteId(String id) {
        PacketBuilder rb = new PacketBuilder(this);
        rb.setUrl("https://discordapp.com/api/invite/" + id);
        rb.setType(RequestType.POST);
        return rb.makeRequest() != null;
    }

    public User getBlankUser() {
        UserImpl user = new UserImpl(getSelfInfo().getUsername(), getSelfInfo().getId(), "Me", this);
        user.setAvatarId(getSelfInfo().getAvatarId());
        user.setAvatar(getSelfInfo().getAvatar());
        return user;
    }

    public Login getLoginTokens() {
        return this.loginTokens;
    }

    public List<User> getAvailableDms() {
        return this.availableDms;
    }

    public List<Server> getAvailableServers() {
        return this.availableServers;
    }

    public Map<String, Group> getUserGroups() {
        return this.userGroups;
    }

    public boolean isDebugMode() {
        return this.debugMode;
    }

    public void setDebugMode(final boolean debugMode) {
        this.debugMode = debugMode;
    }

    public boolean isAllowLogMessages() {
        return this.allowLogMessages;
    }

    public void setAllowLogMessages(final boolean allowLogMessages) {
        this.allowLogMessages = allowLogMessages;
    }

    public EventManager getEventManager() {
        return this.eventManager;
    }

    public RequestManager getRequestManager() {
        return this.requestManager;
    }

    public void setRequestManager(final RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public SelfData getSelfInfo() {
        return this.selfInfo;
    }

    public void setSelfInfo(final SelfData selfInfo) {
        this.selfInfo = selfInfo;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void setLoaded() {
        this.loaded = true;
        getEventManager().executeEvent(new LoadedEvent(this));
    }

    public String getAs() {
        return this.as;
    }

    public void setAs(final String as) {
        this.as = as;
    }

    public AccountManager getAccountManager() {
        return this.accountManager;
    }

    public void setAccountManager(final AccountManager accountManager) {
        this.accountManager = accountManager;
    }

    public Long getStartedTime() {
        return this.startedTime;
    }

	public void clear() {
		availableDms.clear();
		availableServers.clear();
		availableServers.clear();
		userGroups.clear();
	}
}
