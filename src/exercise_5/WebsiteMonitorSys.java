package exercise_5;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// The common interface for all comparison strategies
interface WebsiteComparisonStrategy {
    boolean isContentDifferent(String oldContent, String newContent);
}

// Strategy 1: Identical content size
class SizeComparisonStrategy implements WebsiteComparisonStrategy {
    @Override
    public boolean isContentDifferent(String oldContent, String newContent) {
        if (oldContent == null || newContent == null) return true;
        // Just checks if the length of the string has changed
        return oldContent.length() != newContent.length();
    }
}

// Strategy 2: Identical HTML content
class HtmlComparisonStrategy implements WebsiteComparisonStrategy {
    @Override
    public boolean isContentDifferent(String oldContent, String newContent) {
        if (oldContent == null || newContent == null) return true;
        // Strictly checks if the raw HTML strings are exactly the same
        return !oldContent.equals(newContent);
    }
}

// Strategy 3: Identical text content (ignoring HTML tags)
class TextComparisonStrategy implements WebsiteComparisonStrategy {
    @Override
    public boolean isContentDifferent(String oldContent, String newContent) {
        if (oldContent == null || newContent == null) return true;

        // A simple trick to strip out HTML tags using a regular expression, leaving just the text
        String oldTextOnly = oldContent.replaceAll("<[^>]*>", "").trim();
        String newTextOnly = newContent.replaceAll("<[^>]*>", "").trim();

        return !oldTextOnly.equals(newTextOnly);
    }
}

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
            site.fetchCurrentContent("<html>Simulated new content downloaded from the internet</html>");
        }
    }
}

// DOMAIN CLASSES

class Website implements Subject {
    private String url;
    private String savedContent; // Stores the last known state of the website
    private WebsiteComparisonStrategy comparisonStrategy; // The "brain"
    private List<Observer> observers = new ArrayList<>();

    public Website(String url) {
        this.url = url;
        this.savedContent = "";
        // Default to checking the raw HTML if no strategy is specified
        this.comparisonStrategy = new HtmlComparisonStrategy();
    }

    public String getUrl() { return this.url; }

    // Let the user swap strategies on the fly!
    public void setComparisonStrategy(WebsiteComparisonStrategy newStrategy) {
        this.comparisonStrategy = newStrategy;
    }

    @Override
    public void attach(Observer o) { observers.add(o); }

    @Override
    public void detach(Observer o) { observers.remove(o); }

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update("AUTOMATIC ALERT: Changes detected on " + this.url);
        }
    }

    // Updated to use the Strategy Pattern
    public void fetchCurrentContent(String newlyDownloadedContent) {
        System.out.println("[System] Checking " + url + "...");

        // Use whichever strategy is currently equipped to do the math
        boolean hasChanged = comparisonStrategy.isContentDifferent(savedContent, newlyDownloadedContent);

        if (hasChanged) {
            System.out.println("  -> Change detected! Updating saved data and notifying users.");
            this.savedContent = newlyDownloadedContent;
            notifyObservers();
        } else {
            System.out.println("  -> No changes found based on current rules.");
        }
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