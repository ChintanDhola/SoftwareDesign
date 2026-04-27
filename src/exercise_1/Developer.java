package exercise_1;

public class Developer {

    // 5 Attributes
    private String name;
    private int age;
    private String primaryLanguage;
    private int yearsOfExperience;
    private boolean isFullStack;

    // Constructor (Updated to use all parameters and setters)
    public Developer(String name, int age, String primaryLanguage, int yearsOfExperience, boolean isFullStack) {
        setName(name);
        setAge(age);
        setPrimaryLanguage(primaryLanguage);
        setYearsOfExperience(yearsOfExperience);
        setFullStack(isFullStack);
    }

    // Setters with Integrity Checks
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Developer name cannot be null or empty.");
        }
        this.name = name;
    }

    public void setAge(int age) {
        if (age < 16 || age > 99) {
            throw new IllegalArgumentException("Age must be between 16 and 99.");
        }
        this.age = age;
    }

    public void setYearsOfExperience(int years) {
        if (years < 0) {
            throw new IllegalArgumentException("Experience cannot be negative.");
        }
        if (years > (this.age - 10)) {
            throw new IllegalArgumentException("Years of experience is mathematically invalid given the developer's age.");
        }
        this.yearsOfExperience = years;
    }

    // Standard Setters
    public void setPrimaryLanguage(String language) {
        this.primaryLanguage = language;
    }

    public void setFullStack(boolean isFullStack) {
        this.isFullStack = isFullStack;
    }

    // Method to print the object's state exactly like the UML diagram
    public void displayObjectState() {
        System.out.println("--- me : Developer ---");
        System.out.println("name = \"" + this.name + "\"");
        System.out.println("age = " + this.age);
        System.out.println("primaryLanguage = \"" + this.primaryLanguage + "\"");
        System.out.println("yearsOfExperience = " + this.yearsOfExperience);
        System.out.println("isFullStack = " + this.isFullStack);
        System.out.println("----------------------");
    }
}