package exercise_1;

public class Me_developer {
    public static void main(String[] args) {
        Developer me = new Developer("Chintan Dhola", 22, "Java");
        me.setYearsOfExperience(2);
        me.setFullStack(false);

        me.displayObjectState();
    }
}