package exercise_5;

public class Main {
    public static void main(String[] args) {

        System.out.println("[System Setup]");
        WebsiteMonitorSys monitorSystem = new WebsiteMonitorSys();
        Website testSite = new Website("https://www.frankfurt-university.de");
        monitorSystem.addWebsite(testSite);

        // Create two users to prove the Observer pattern notifies multiple people
        User me = new User("Chintan Dhola");
        User classmate = new User("Kartik Chauhan");
        monitorSystem.registerUser(me);
        monitorSystem.registerUser(classmate);

        // Attach them to the website with different alert methods
        me.subscribeToWebsite(testSite, new EmailChannel());
        classmate.subscribeToWebsite(testSite, new SMSChannel());

        // Initial load of the website
        System.out.println("\n[Initial Website Load]");
        testSite.fetchCurrentContent("<html>Welcome to the University</html>");

        System.out.println("\nTesting Strategy Pattern");

        System.out.println("\n[Test A] Using Default HTML Strategy");

        testSite.fetchCurrentContent("<body>Welcome to the University</body>");


        System.out.println("\n[Test B] Switching to Text Strategy");

        testSite.setComparisonStrategy(new TextComparisonStrategy());

        testSite.fetchCurrentContent("<div>Welcome to the University</div>");


        System.out.println("\n[Test C] Changing the actual text");

        testSite.fetchCurrentContent("<div>Classes are cancelled today!</div>");
    }
}