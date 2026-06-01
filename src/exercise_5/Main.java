package exercise_5;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Exercise 5: Observer Pattern Test ---\n");

        // Initialize the central system
        WebsiteMonitorSys monitorSystem = new WebsiteMonitorSys();

        // Create the website we want to track and add it to the system
        Website uniSite = new Website("https://www.frankfurt-university.de");
        monitorSystem.addWebsite(uniSite);

        System.out.println("\n[Setup] Registering users and subscriptions...");

        // Create your user profile and choose your alert method
        User me = new User("Chintan Dhola");
        monitorSystem.registerUser(me);
        CommunicationChannel myEmailAlert = new EmailChannel();

        User classmate = new User("Kartik Chauhan");
        monitorSystem.registerUser(classmate);
        CommunicationChannel classmateSmsAlert = new SMSChannel();

        // Connect the Observers to the Subject
        me.subscribeToWebsite(uniSite, myEmailAlert);
        classmate.subscribeToWebsite(uniSite, classmateSmsAlert);

        // Run the system check
        monitorSystem.triggerGlobalCheck();
    }
}