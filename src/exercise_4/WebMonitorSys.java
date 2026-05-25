package exercise_4;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// 1. THE MAIN SYSTEM
public class WebMonitorSys {
    private List<User> users = new ArrayList<>();
    private List<Website> websites = new ArrayList<>();

    public void registerUser(User u) {
        users.add(u);
        System.out.println("User registered: " + u.getName());
    }

    public void addWebsite(Website w) {
        websites.add(w);
    }

    public void startMonitoring() {
        System.out.println("System is now monitoring websites...");
    }

    public void processUpdates() {
        System.out.println("Processing updates for all users...");
    }
}

// 2. USER & SUBSCRIPTION
class User {
    private int userId;
    private String name;
    private String email;
    private List<Subscription> userSubscriptions = new ArrayList<>();

    public User(int userId, String name, String email) {
        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public String getName() { return this.name; }

    public void createSubscription(Subscription sub) {
        userSubscriptions.add(sub);
    }

    public void editSubscription() { /* Mockup */ }
    public void removeSubscription() { /* Mockup */ }
}

class Subscription {
    private String frequency;
    private boolean active;
    private Website targetWebsite;
    private CommunicationChannel alertChannel;

    public Subscription(Website site, CommunicationChannel channel) {
        this.targetWebsite = site;
        this.alertChannel = channel;
        this.active = true;
    }

    public boolean isCheckRequired() { return active; }
    public void updatePreferences() {}
    public void cancel() { this.active = false; }

    public void generateNotification() {
        Notification alert = new Notification("Update found for " + targetWebsite.getUrl());
        alertChannel.sendNotification(alert);
    }
}

// 3. WEBSITE
class Website {
    private String url;
    private String lastContentHash;

    public Website(String url) {
        this.url = url;
    }

    public String getUrl() { return this.url; }
    public void checkForChanges() {}
    public void fetchCurrentContent() {}
}

// 4. NOTIFICATIONS & CHANNELS
class Notification {
    private String message;
    private Date timestamp;

    public Notification(String msg) {
        this.message = msg;
        this.timestamp = new Date();
    }

    public String getMessage() { return message; }
}

abstract class CommunicationChannel {
    public abstract void sendNotification(Notification n);
}

class EmailChannel extends CommunicationChannel {
    @Override
    public void sendNotification(Notification n) {
        System.out.println("[EMAIL ALERT]: " + n.getMessage());
    }
}

class SMSChannel extends CommunicationChannel {
    @Override
    public void sendNotification(Notification n) {
        System.out.println("[SMS ALERT]: " + n.getMessage());
    }
}

class PushChannel extends CommunicationChannel {
    @Override
    public void sendNotification(Notification n) {
        System.out.println("[PUSH NOTIFICATION]: " + n.getMessage());
    }
}