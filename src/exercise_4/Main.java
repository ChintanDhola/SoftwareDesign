package exercise_4;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Starting Website Monitor Test ---");

        // 1. Initialize the main system
        WebMonitorSys monitorSystem = new WebMonitorSys();

        // 2. Create a user (using your name!)
        User myUser = new User(111, "Chintan Dhola", "chintandhola2811@gmail.com");
        monitorSystem.registerUser(myUser);

        // 3. Add a website to watch
        Website schoolSite = new Website("https://www.frankfurt-university.de");
        monitorSystem.addWebsite(schoolSite);

        // 4. Choose how we want to be notified (Let's use Email for this test)
        CommunicationChannel alertMethod = new EmailChannel();

        // 5. Create the subscription and link everything together
        Subscription mySubscription = new Subscription(schoolSite, alertMethod);
        myUser.createSubscription(mySubscription);

        // 6. Run the system methods
        monitorSystem.startMonitoring();
        monitorSystem.processUpdates();

        // 7. Simulate what happens when a website actually changes
        System.out.println("\n--- Simulating a website change... ---");
        if (mySubscription.isCheckRequired()) {
            mySubscription.generateNotification();
        }
    }
}