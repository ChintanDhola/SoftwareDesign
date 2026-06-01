package exercise_5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// INTERFACES FOR OBSERVER PATTERN
interface Observer {
    void update(String message);
}

interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers();
}

// THE CORE SYSTEM
public class WebsiteMonitorSys {
    private List<User> users = new ArrayList<>();
    private List<Website> websites = new ArrayList<>();

    public void registerUser(User u) {
        users.add(u);
        System.out.println("User registered in system: " + u.getName());
    }

    public void addWebsite(Website w) {
        websites.add(w);
        System.out.println("Website added to global monitoring list: " + w.getUrl());
    }

    // Notice how clean this is now! The system doesn't manage notifications anymore
    public void triggerGlobalCheck() {
        System.out.println("\n[System] Running scheduled check on all websites...");
        for (Website site : websites) {
            site.fetchCurrentContent();
        }
    }
}

// DOMAIN CLASSES

class Website implements Subject {
    private String url;
    // This list holds all the observers (subscriptions) watching this website
    private List<Observer> observers = new ArrayList<>();

    public Website(String url) {
        this.url = url;
    }

    public String getUrl() { return this.url; }

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update("AUTOMATIC ALERT: Update found for " + this.url);
        }
    }

    // Simulating the website checking the internet for changes
    public void fetchCurrentContent() {
        System.out.println("[Website Backend] Detected changes on " + url);
        // As soon as a change is found, it automatically shouts out to everyone listening!
        notifyObservers();
    }
}

class User {
    private String name;
    private List<Subscription> userSubscriptions = new ArrayList<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() { return this.name; }

    // Creates the subscription and immediately links the Observer to the Subject
    public void subscribeToWebsite(Website site, CommunicationChannel channel) {
        Subscription sub = new Subscription(channel);
        userSubscriptions.add(sub);
        site.attach(sub); // This is the magic connection for the pattern!
        System.out.println(" -> " + name + " successfully subscribed to " + site.getUrl());
    }
}

class Subscription implements Observer {
    private boolean active;
    private CommunicationChannel alertChannel;

    public Subscription(CommunicationChannel channel) {
        this.alertChannel = channel;
        this.active = true;
    }

    public void cancel() { this.active = false; }

    // This is called automatically by the Website (Subject)
    @Override
    public void update(String message) {
        if (active) {
            Notification alert = new Notification(message);
            alertChannel.sendNotification(alert);
        }
    }
}

// NOTIFICATIONS & CHANNELS

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
        System.out.println("    [EMAIL SENT] " + n.getMessage());
    }
}

class SMSChannel extends CommunicationChannel {
    @Override
    public void sendNotification(Notification n) {
        System.out.println("    [SMS SENT] " + n.getMessage());
    }
}

class PushChannel extends CommunicationChannel {
    @Override
    public void sendNotification(Notification n) {
        System.out.println("    [PUSH NOTIFICATION] " + n.getMessage());
    }
}